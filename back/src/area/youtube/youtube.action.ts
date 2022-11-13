import { Area } from "@prisma/client";
import { google } from "googleapis";

import ENV from "../../env";
import { AreaService } from "../../services";
import * as ServiceHelper from "../../helpers/service.helpers";

const checkUploadedVideo = async (area: Area): Promise<string | null> => {
  const oAuth2Client = await ServiceHelper.getGoogleOauthClient(area.userId);

  if (!oAuth2Client) return null;

  const youtube = google.youtube({ version: "v3", auth: oAuth2Client });

  const channelInfos = await youtube.search.list({
    type: ["channel"],
    maxResults: 1,
    part: ["snippet"],
    q: area.actionParam,
  });

  if (!channelInfos.data.items || !channelInfos.data.items[0].id?.channelId)
    return null;

  const channel = (
    await youtube.channels.list({
      id: [channelInfos.data.items[0].id.channelId],
      part: ["contentDetails"],
    })
  ).data;

  if (
    !channel.items ||
    !channel.items[0].contentDetails?.relatedPlaylists?.uploads
  )
    return null;

  const videos = await youtube.playlistItems.list({
    maxResults: 1,
    part: ["snippet"],
    playlistId: channel.items[0].contentDetails.relatedPlaylists.uploads,
  });

  if (!videos.data.items || !videos.data.items[0]) return null;

  const lastVideo = videos.data.items[0];

  if (
    !lastVideo.snippet?.publishedAt ||
    !lastVideo.snippet?.title ||
    !lastVideo.snippet.channelTitle
  )
    return null;

  if (area.lastActionValue === null) {
    await AreaService.updateAreaValues(area.id, "");
    return null;
  }

  const params = {
    name: lastVideo.snippet.title,
    channelName: lastVideo.snippet.channelTitle,
  };

  if (new Date(lastVideo.snippet.publishedAt) > area.lastActionFetch) {
    await AreaService.updateAreaValues(area.id, "");
    return ServiceHelper.injectParamInReaction<typeof params>(
      area.reactionParam,
      params,
    );
  }

  await AreaService.updateAreaValues(area.id, "");

  return null;
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

  const statistics = video[0].statistics;

  if (!statistics || !statistics.likeCount || !statistics.viewCount)
    return null;

  const params = {
    like: statistics.likeCount,
    viewCount: statistics.viewCount,
  };

  if (area.lastActionValue === null) {
    await AreaService.updateAreaValues(area.id, statistics.likeCount);
    return null;
  }

  if (parseInt(statistics.likeCount) > parseInt(area.lastActionValue)) {
    await AreaService.updateAreaValues(area.id, statistics.likeCount);
    return ServiceHelper.injectParamInReaction<typeof params>(
      area.reactionParam,
      params,
    );
  }

  await AreaService.updateAreaValues(area.id, statistics.likeCount);

  return null;
};

const checkNewVideoLiked = async (area: Area): Promise<string | null> => {
  const oAuth2Client = await ServiceHelper.getGoogleOauthClient(area.userId);

  if (!oAuth2Client) return null;

  const youtube = google.youtube({ version: "v3", auth: oAuth2Client });

  const channel = (
    await youtube.channels.list({
      part: ["contentDetails"],
      mine: true,
    })
  ).data;

  if (!channel.items) return null;

  if (!channel.items[0].contentDetails?.relatedPlaylists?.likes) return null;

  const playlist = (
    await youtube.playlistItems.list({
      part: ["snippet"],
      playlistId: channel.items[0].contentDetails?.relatedPlaylists?.likes,
    })
  ).data;

  if (!playlist.pageInfo?.totalResults || !playlist.items) return null;

  const lastVideo = playlist.items[0];
  if (
    area.lastActionValue === null ||
    !lastVideo.snippet ||
    !lastVideo.snippet.videoOwnerChannelTitle ||
    !lastVideo.snippet.title
  ) {
    await AreaService.updateAreaValues(
      area.id,
      String(playlist.pageInfo.totalResults),
    );
    return null;
  }

  const params = {
    channel: lastVideo.snippet.videoOwnerChannelTitle,
    title: lastVideo.snippet.title,
  };

  if (playlist.pageInfo.totalResults > parseInt(area.lastActionValue)) {
    await AreaService.updateAreaValues(
      area.id,
      String(playlist.pageInfo.totalResults),
    );
    return ServiceHelper.injectParamInReaction<typeof params>(
      area.reactionParam,
      params,
    );
  }

  await AreaService.updateAreaValues(
    area.id,
    String(playlist.pageInfo.totalResults),
  );

  return null;
};

export { checkUploadedVideo, checkVideoLike, checkNewVideoLiked };
