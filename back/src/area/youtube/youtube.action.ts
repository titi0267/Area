import { Area } from "@prisma/client";
import console from "console";
import { google } from "googleapis";
import ENV from "../../env";
import { AreaService } from "../../services";

const checkUploadedVideo = async (area: Area): Promise<Boolean> => {
  const youtube = google.youtube({ version: "v3", auth: ENV.googleApiKey });

  await AreaService.updateAreaValues(area.id, null);

  const channel = await youtube.channels.list({
    forUsername: area.actionParam,
    part: ["contentDetails"],
  });

  if (
    !channel.data.items ||
    !channel.data.items[0].contentDetails?.relatedPlaylists?.uploads
  )
    return false;

  const videos = await youtube.playlistItems.list({
    maxResults: 1,
    part: ["snippet"],
    playlistId: channel.data.items[0].contentDetails.relatedPlaylists.uploads,
  });

  if (!videos.data.items || !videos.data.items[0].snippet?.publishedAt)
    return false;

  if (new Date(videos.data.items[0].snippet.publishedAt) > area.lastActionFetch)
    return true;

  return false;
};

const checkVideoLike = async (area: Area): Promise<Boolean> => {
  const youtube = google.youtube({ version: "v3", auth: ENV.googleApiKey });

  const video = (
    await youtube.videos.list({
      id: [area.actionParam],
      part: ["statistics"],
    })
  ).data.items;

  if (!video || video.length < 1) return false;

  const statistics = video[0].statistics;

  if (!statistics || !statistics.likeCount) return false;

  if (area.lastActionValue === null) {
    await AreaService.updateAreaValues(area.id, statistics.likeCount);
    return false;
  }

  if (parseInt(statistics.likeCount) > parseInt(area.lastActionValue)) {
    await AreaService.updateAreaValues(area.id, statistics.likeCount);
    return true;
  }

  return false;
};

export { checkUploadedVideo, checkVideoLike };
