import { Service } from "../types/areaServices/areaServices.types";
import * as YoutubeActions from "../ServiceRequest/youtube/youtube.action";
import * as TwitterReaction from "../ServiceRequest/twitter/twitter.reaction";
import * as DiscordReaction from "../ServiceRequest/discord/discord.reaction";

export const SERVICES: Service[] = [
  {
    id: 1,
    serviceName: "Youtube",
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
];
