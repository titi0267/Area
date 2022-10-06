import { Service } from "../types/areaSeervices/areaServices.types";
import * as YoutubeActions from "../ServiceRequest/youtube/youtube.action";
import * as TwitterReaction from "../ServiceRequest/twitter/twitter.reaction";

export const SERVICES: Service[] = [
  {
    id: 1,
    serviceName: "Youtube",
    action: [
      {
        id: 1,
        actionName: "NewVideoUploaded",
        actionParamName: "Channel Name",
        fct: YoutubeActions.checkUploadedVideo,
      },
    ],
    reaction: [],
  },
  {
    id: 2,
    serviceName: "Twitter",
    action: [],
    reaction: [
      {
        id: 1,
        reactionName: "PostTweet",
        reactionParamName: "Text to write before",
        fct: TwitterReaction.postNewTweet,
      },
    ],
  },
];
