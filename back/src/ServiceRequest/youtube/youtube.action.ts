import { Area } from "@prisma/client";
import { google } from "googleapis";

import ENV from "../../env";
import { AreaService } from "../../services";
import * as ServiceHelper from "../../helpers/service.helpers";

const checkUploadedVideo = async (area: Area): Promise<string | null> => {
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
    return null;

  const videos = await youtube.playlistItems.list({
    maxResults: 1,
    part: ["snippet"],
    playlistId: channel.data.items[0].contentDetails.relatedPlaylists.uploads,
  });

  if (!videos.data.items || !videos.data.items[0]) return null;

  const lastVideo = videos.data.items[0];

  if (
    !lastVideo.snippet?.publishedAt ||
    !lastVideo.snippet?.title ||
    !lastVideo.snippet.channelTitle
  )
    return null;

  if (new Date(lastVideo.snippet.publishedAt) > area.lastActionFetch)
    return null;

  const params = {
    name: lastVideo.snippet.title,
    channelName: lastVideo.snippet.channelTitle,
  };

  return area.reactionParam;
};

const checkVideoLike = async (area: Area): Promise<string | null> => {
  const youtube = google.youtube({ version: "v3", auth: ENV.googleApiKey });

  const videoId = ServiceHelper.getYoutubeVideoId(area.actionParam);

  if (!videoId) return null;

  const video = (
    await youtube.videos.list({
      id: [videoId],
      part: ["statistics"],
    })
  ).data.items;

  if (!video || video.length < 1) return null;

  console.log(video);

  const statistics = video[0].statistics;

  if (!statistics || !statistics.likeCount || !statistics.dislikeCount)
    return null;

  const params = {
    like: statistics.likeCount,
    dislike: statistics.dislikeCount,
  };

  if (area.lastActionValue === null) {
    await AreaService.updateAreaValues(area.id, statistics.likeCount);
    return null;
  }

  if (parseInt(statistics.likeCount) > parseInt(area.lastActionValue)) {
    await AreaService.updateAreaValues(area.id, statistics.likeCount);
    return area.reactionParam;
  }

  return null;
};

export { checkUploadedVideo, checkVideoLike };
