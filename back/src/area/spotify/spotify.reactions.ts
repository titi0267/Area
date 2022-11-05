import * as ServiceHelper from "../../helpers/service.helpers";

const addTrackToPlaylist = async (
  reactionParam: string,
  userId: number,
): Promise<void> => {
  const spotifyCredential = await ServiceHelper.getSpotifyClient(userId);

  if (!spotifyCredential) return;

  const spotifyApi = spotifyCredential.client;

  spotifyApi.setRefreshToken(spotifyCredential.token);
  const accessToken = (await spotifyApi.refreshAccessToken()).body.access_token;
  spotifyApi.setAccessToken(accessToken);

  if (reactionParam == undefined || null) return;

  const myPlaylists = await spotifyApi.getUserPlaylists();
  let areaPlaylistId = null;

  areaPlaylistId = myPlaylists.body.items.find(elem => {
    elem.name === "Area";
  })?.id;

  if (!areaPlaylistId) {
    const newPlaylistId = await spotifyApi.createPlaylist("Area", {
      description: "This is the area playlist",
      collaborative: false,
      public: true,
    });
    areaPlaylistId = newPlaylistId.body.id;
  }
  const trackToAdd = await spotifyApi.searchTracks(reactionParam);

  if (trackToAdd.body.tracks?.items[0].id == undefined) return;

  await spotifyApi.addTracksToPlaylist(areaPlaylistId, [
    trackToAdd.body.tracks?.items[0].uri,
  ]);
};

const updateVolume = async (
  reactionParam: string,
  userId: number,
): Promise<void> => {
  const spotifyCredential = await ServiceHelper.getSpotifyClient(userId);

  if (!spotifyCredential) return;

  const spotifyApi = spotifyCredential.client;

  spotifyApi.setRefreshToken(spotifyCredential.token);
  const accessToken = (await spotifyApi.refreshAccessToken()).body.access_token;
  spotifyApi.setAccessToken(accessToken);

  const device = await spotifyApi.getMyDevices();

  device.body.devices.forEach(element => {
    if (element.is_active == true) {
      if (element.type != "Smartphone" && element.id != null) {
        spotifyApi.setVolume(100, { device_id: element.id });
      }
    }
  });
};

const startMusic = async (
  reactionParam: string,
  userId: number,
): Promise<void> => {
  const spotifyCredential = await ServiceHelper.getSpotifyClient(userId);

  if (!spotifyCredential) return;

  const spotifyApi = spotifyCredential.client;

  spotifyApi.setRefreshToken(spotifyCredential.token);
  const accessToken = (await spotifyApi.refreshAccessToken()).body.access_token;
  spotifyApi.setAccessToken(accessToken);

  const trackToPlay = await spotifyApi.searchAlbums(reactionParam);

  if (trackToPlay.body.albums == undefined) return;

  await spotifyApi.play({
    context_uri: trackToPlay.body.albums.items[0].uri,
  });
};

const addMusicToQueue = async (
  reactionParam: string,
  userId: number,
): Promise<void> => {
  const spotifyCredential = await ServiceHelper.getSpotifyClient(userId);

  if (!spotifyCredential) return;

  const spotifyApi = spotifyCredential.client;

  spotifyApi.setRefreshToken(spotifyCredential.token);
  const accessToken = (await spotifyApi.refreshAccessToken()).body.access_token;
  spotifyApi.setAccessToken(accessToken);

  const trackToAdd = await spotifyApi.searchTracks(reactionParam);

  if (trackToAdd.body.tracks?.items[0].uri == undefined) return;

  spotifyApi.addToQueue(trackToAdd.body.tracks?.items[0].uri);
};

const repeatMusic = async (
  reactionParam: string,
  userId: number,
): Promise<void> => {
  const spotifyCredential = await ServiceHelper.getSpotifyClient(userId);

  if (!spotifyCredential) return;

  const spotifyApi = spotifyCredential.client;

  spotifyApi.setRefreshToken(spotifyCredential.token);
  const accessToken = (await spotifyApi.refreshAccessToken()).body.access_token;
  spotifyApi.setAccessToken(accessToken);

  await spotifyApi.setRepeat("track");
};

export {
  addTrackToPlaylist,
  updateVolume,
  startMusic,
  addMusicToQueue,
  repeatMusic,
};
