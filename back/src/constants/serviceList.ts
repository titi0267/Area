import { Service } from "../types/areaServices/areaServices.types";
import * as YoutubeActions from "../area/youtube/youtube.action";
import * as TwitterReaction from "../area/twitter/twitter.reaction";
import * as DiscordReaction from "../area/discord/discord.reaction";

export const SERVICES: Service[] = [
  {
    id: 1,
    serviceName: "Github",
    imageUrl:
      "https://www.edigitalagency.com.au/wp-content/uploads/Youtube-logo-white-only-png.png",
    backgroundColor: "#FF0000",
    actions: [
      {
        id: 1,
        actionName: "NewVideoUploaded",
        actionParamName: "Channel Name",
        fct: YoutubeActions.checkUploadedVideo,
      },
      {
        id: 2,
        actionName: "NewLikeOnAVideo",
        actionParamName: "Video Id",
        fct: YoutubeActions.checkVideoLike,
      },
    ],
    reactions: [],
  },
  {
    id: 2,
    serviceName: "Twitter",
    imageUrl:
      "https://toppng.com/uploads/preview/twitter-icon-white-transparent-11549537259z0sowbg17j.png",
    backgroundColor: "#1DA1F2",
    actions: [],
    reactions: [
      {
        id: 1,
        reactionName: "PostTweet",
        reactionParamName: "Text to write before",
        fct: TwitterReaction.postNewTweet,
      },
    ],
  },
  {
    id: 3,
    serviceName: "Discord",
    imageUrl:
      "https://www.pngfind.com/pngs/m/28-283551_discord-discord-icon-transparent-white-hd-png-download.png",
    backgroundColor: "#5865F2",
    actions: [],
    reactions: [
      {
        id: 1,
        reactionName: "Send a message to général",
        reactionParamName: "Message to write",
        fct: DiscordReaction.sendMessageToServer,
      },
    ],
  },
  {
    id: 4,
    serviceName: "Spotify",
    imageUrl:
      "https://i.pinimg.com/originals/33/d7/d4/33d7d461d1d17c598319f3efd955e88c.png",
    backgroundColor: "#1DB954",
    actions: [
      {
        id: 1,
        actionName: "Test spotify",
        actionParamName: "Channel Name",
        fct: YoutubeActions.checkUploadedVideo,
      },
      {
        id: 2,
        actionName: "NewLikeOnAVideo",
        actionParamName: "Video Id",
        fct: YoutubeActions.checkVideoLike,
      },
    ],
    reactions: [],
  },
];
