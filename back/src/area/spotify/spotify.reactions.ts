import { Area } from "@prisma/client";
import { AreaService, TokenService } from "../../services";
import ENV from "../../env";
import * as ServiceHelper from "../../helpers/service.helpers";

const addTrackToPlaylist = async (
  reactionParam: string,
  userId: number,
): Promise<void> => {
  const spotifyApi = await ServiceHelper.getSpotifyClient(userId);

  if (!spotifyApi) return;
  const playlistId = ServiceHelper.getSpotifyPlaylistId(reactionParam);

  if (playlistId == null) return;

  const myPlaylists = await spotifyApi.getUserPlaylists();
  let areaPlaylistId = null;
  myPlaylists.body.items.forEach(element => {
    if (element.name == "Area") {
      areaPlaylistId = element.id;
    }
  });
  if (areaPlaylistId == null) {
    const newPlaylistId = await spotifyApi.createPlaylist("Area", {
      description: "This is the area playlist",
      collaborative: false,
      public: true,
    });

    const trackToadd = await spotifyApi.searchTracks("Tu veux mon zizi");

    if (trackToadd.body.tracks?.items[0].id == undefined) return;

    await spotifyApi.addTracksToPlaylist(newPlaylistId.body.id, [
      trackToadd.body.tracks?.items[0].uri,
    ]);
  } else {
    const trackToadd = await spotifyApi.searchTracks("Tu veux mon zizi");

    if (trackToadd.body.tracks?.items[0].id == undefined) return;

    await spotifyApi.addTracksToPlaylist(areaPlaylistId, [
      trackToadd.body.tracks?.items[0].uri,
    ]);
  }
};

const updateVolume = async (
  reactionParam: string,
  userId: number,
): Promise<void> => {
  const spotifyApi = await ServiceHelper.getSpotifyClient(userId);

  if (!spotifyApi) return;
  const device = await spotifyApi.getMyDevices();
  device.body.devices.forEach(element => {
    if (element.is_active == true) {
      if (element.type != "Smartphone" && element.id != null) {
        spotifyApi.setVolume(100, { device_id: element.id });
      }
    }
  });
};

export { addTrackToPlaylist, updateVolume };
