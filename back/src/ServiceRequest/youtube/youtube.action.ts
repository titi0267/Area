import { google } from "googleapis";
import ENV from "../../env";

const checkUploadedVideo = async (
  actionParam: string,
  userId: string,
  lastUpdate: Date,
): Promise<Boolean> => {
  const youtube = google.youtube({ version: "v3", auth: ENV.googleApiKey });

  const channel = await youtube.channels.list({
    forUsername: actionParam,
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

  if (new Date(videos.data.items[0].snippet.publishedAt) > lastUpdate)
    return true;

  return false;
};

export { checkUploadedVideo };
