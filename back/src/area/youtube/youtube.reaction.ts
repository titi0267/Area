import { google } from "googleapis";

import * as ServiceHelper from "../../helpers/service.helpers";

const likeVideo = async (
  reactionParam: string,
  userId: number,
): Promise<void> => {
  const oAuth2Client = await ServiceHelper.getGoogleOauthClient(userId);

  if (!oAuth2Client) return;

  const youtube = google.youtube({ version: "v3", auth: oAuth2Client });

  const videos = (
    await youtube.search.list({
      part: ["snippet"],
      q: reactionParam,
      type: ["video"],
    })
  ).data;

  if (
    !videos.items ||
    !videos.items[0] ||
    !videos.items[0].id ||
    !videos.items[0].id.videoId
  )
    return;

  youtube.videos.rate({
    id: videos.items[0].id?.videoId,
    rating: "like",
  });
};

const dislikeVideo = async (
  reactionParam: string,
  userId: number,
): Promise<void> => {
  const oAuth2Client = await ServiceHelper.getGoogleOauthClient(userId);

  if (!oAuth2Client) return;

  const youtube = google.youtube({ version: "v3", auth: oAuth2Client });

  const videos = (
    await youtube.search.list({
      part: ["snippet"],
      q: reactionParam,
      type: ["video"],
    })
  ).data;

  if (
    !videos.items ||
    !videos.items[0] ||
    !videos.items[0].id ||
    !videos.items[0].id.videoId
  )
    return;

  youtube.videos.rate({
    id: videos.items[0].id?.videoId,
    rating: "dislike",
  });
};

export { likeVideo, dislikeVideo };
