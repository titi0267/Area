import { Area } from "@prisma/client";
import { AreaService, TokenService } from "../../services";
import * as ServiceHelper from "../../helpers/service.helpers";

const checkMusicSkip = async (area: Area): Promise<string | null> => {
  const spotifyCredential = await ServiceHelper.getSpotifyClient(area.userId);

  if (!spotifyCredential) return null;

  const spotifyApi = spotifyCredential.client;

  spotifyApi.setRefreshToken(spotifyCredential.token);
  const accessToken = (await spotifyApi.refreshAccessToken()).body.access_token;
  spotifyApi.setAccessToken(accessToken);

  const currentSong = (await spotifyApi.getMyCurrentPlayingTrack()).body;

  if (currentSong.item?.name == undefined) return null;
  if (area.lastActionValue == null) {
    await AreaService.updateAreaValues(area.id, currentSong.item?.name);
    return null;
  }
  const params = {
    songName: currentSong.item.name,
  };

  if (currentSong.item.name != area.lastActionValue) {
    await AreaService.updateAreaValues(area.id, currentSong.item.name);
    return ServiceHelper.injectParamInReaction<typeof params>(
      area.reactionParam,
      params,
    );
  }
  return null;
};

const checkIsMusicLiked = async (area: Area): Promise<string | null> => {
  const spotifyCredential = await ServiceHelper.getSpotifyClient(area.userId);

  if (!spotifyCredential) return null;

  const spotifyApi = spotifyCredential.client;

  spotifyApi.setRefreshToken(spotifyCredential.token);
  const accessToken = (await spotifyApi.refreshAccessToken()).body.access_token;
  spotifyApi.setAccessToken(accessToken);

  const likedTracks = (await spotifyApi.getMySavedTracks()).body;

  if (area.lastActionValue == null) {
    await AreaService.updateAreaValues(area.id, likedTracks.total.toString());
    return null;
  }

  const params = {
    songName: likedTracks.items[0].track.name,
    artists: likedTracks.items[0].track.artists[0].name,
  };

  if (likedTracks.total > parseInt(area.lastActionValue)) {
    await AreaService.updateAreaValues(area.id, likedTracks.total.toString());
    return ServiceHelper.injectParamInReaction<typeof params>(
      area.reactionParam,
      params,
    );
  }
  await AreaService.updateAreaValues(area.id, likedTracks.total.toString());
  return null;
};

const addTrackToPlaylist = async (area: Area): Promise<string | null> => {
  const spotifyCredential = await ServiceHelper.getSpotifyClient(area.userId);

  if (!spotifyCredential) return null;

  const spotifyApi = spotifyCredential.client;

  spotifyApi.setRefreshToken(spotifyCredential.token);
  const accessToken = (await spotifyApi.refreshAccessToken()).body.access_token;
  spotifyApi.setAccessToken(accessToken);

  const yourPlaylists = await spotifyApi.getUserPlaylists();

  if (yourPlaylists.body.items == undefined) return null;
  let playlistItems = yourPlaylists.body.items.find(
    elem => elem.name == area.actionParam,
  );
  if (playlistItems == undefined) return null;
  let playlist = playlistItems;
  const playlistTracks = await spotifyApi.getPlaylistTracks(playlist.id);

  let lastTrack = playlistTracks.body.items[playlistTracks.body.total];

  if (area.lastActionValue == null) {
    await AreaService.updateAreaValues(area.id, lastTrack.added_at);
    return null;
  }
  if (lastTrack.track == null) return null;
  const params = {
    songAdded: lastTrack.track.name,
    songArtists: lastTrack.track.artists,
  };
  if (lastTrack.added_at > area.lastActionValue) {
    await AreaService.updateAreaValues(area.id, lastTrack.added_at);
    return ServiceHelper.injectParamInReaction<typeof params>(
      area.reactionParam,
      params,
    );
  }
  await AreaService.updateAreaValues(area.id, lastTrack.added_at);
  return null;
};

export { checkMusicSkip, checkIsMusicLiked, addTrackToPlaylist };
