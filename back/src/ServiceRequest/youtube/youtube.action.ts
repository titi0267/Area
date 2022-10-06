import { google } from "googleapis";

const checkUploadedVideo = async (
  actionParam: string,
  userId: string,
  lastUpdate: Date,
): Promise<Boolean> => {
  const youtube = google.youtube("v3");

  const channel = await youtube.channels.list({
    part: ["snippet"],
    id: [actionParam],
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
