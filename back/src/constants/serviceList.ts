import { Service } from "../types/areaServices/areaServices.types";
import * as YoutubeActions from "../area/youtube/youtube.action";
import * as YoutubeReactions from "../area/youtube/youtube.reaction";
import * as DiscordReaction from "../area/discord/discord.reaction";
import * as SpotifyAction from "../area/spotify/spotify.actions";
import * as GithubAction from "../area/github/github.action";
import * as GmailAction from "../area/gmail/gmail.action";
import { FORMAT } from "./paramFormat";
import ENV from "../env";

export const SERVICES: Service[] = [
  {
    id: 1,
    serviceName: "Youtube",
    oauthName: "google",
    imageUrl: `http://${ENV.host}:${ENV.port}/assets/youtube.png`,
    backgroundColor: "#FF0000",
    actions: [
      {
        id: 1,
        actionName: "New Video Uploaded",
        actionParamName: "Channel link",
        fct: YoutubeActions.checkUploadedVideo,
        paramFormat: FORMAT.youtubeChannelUrl,
        description: "A new video has been upload on a channel",
        availableInjectParams: ["name", "channelName"],
      },
      {
        id: 2,
        actionName: "New like on a video",
        actionParamName: "Video link",
        fct: YoutubeActions.checkVideoLike,
        paramFormat: FORMAT.youtubeVideoUrl,
        description: "A new person has liked a specific video",
        availableInjectParams: ["like", "viewCount"],
      },
      {
        id: 3,
        actionName: "New Liked Video",
        actionParamName: "",
        paramFormat: null,
        fct: YoutubeActions.checkNewVideoLiked,
        description: "You liked a new video",
        availableInjectParams: ["channel", "title"],
      },
    ],
    reactions: [
      {
        id: 1,
        reactionName: "Like a video",
        reactionParamName: "Video to like",
        paramFormat: null,
        description: "Your account will like a specific video",
        fct: YoutubeReactions.likeVideo,
      },
      {
        id: 2,
        reactionName: "Dislike a video",
        reactionParamName: "Video to dislike",
        paramFormat: null,
        description: "Your account will dislike a specific video",
        fct: YoutubeReactions.dislikeVideo,
      },
    ],
  },
  {
    id: 2,
    serviceName: "Discord",
    imageUrl: `http://${ENV.host}:${ENV.port}/assets/discord.png`,
    backgroundColor: "#5865F2",
    oauthName: null,
    actions: [],
    reactions: [
      {
        id: 1,
        reactionName: "Send a message",
        reactionParamName: "Message to write",
        paramFormat: null,
        description:
          "The discord bot send a message to the first server text channel",
        fct: DiscordReaction.sendMessageToServer,
      },
    ],
  },
  {
    id: 3,
    serviceName: "Spotify",
    imageUrl: `http://${ENV.host}:${ENV.port}/assets/spotify.png`,
    backgroundColor: "#1DB954",
    oauthName: "spotify",
    actions: [
      {
        id: 1,
        actionName: "Skip to next song",
        actionParamName: "Skip song",
        paramFormat: null,
        description: "You skipped a song",
        fct: SpotifyAction.checkMusicSkip,
        availableInjectParams: ["songName"],
      },
      {
        id: 2,
        actionName: "New liked song",
        actionParamName: "Liked track",
        paramFormat: null,
        description: "You liked a new song",
        fct: SpotifyAction.checkIsMusicLiked,
        availableInjectParams: ["songName", "artists"],
      },
    ],
    reactions: [],
  },
  {
    id: 4,
    serviceName: "Github",
    imageUrl: `http://${ENV.host}:${ENV.port}/assets/github.png`,
    backgroundColor: "#000000",
    oauthName: "github",
    actions: [
      {
        id: 1,
        actionName: "New follower",
        actionParamName: "",
        paramFormat: null,
        description: "You have a new follower",
        fct: GithubAction.checkNewFollowingUser,
        availableInjectParams: ["lastFollowingUserName"],
      },
    ],
    reactions: [],
  },
  {
    id: 5,
    serviceName: "Gmail",
    backgroundColor: "FF0000",
    imageUrl: `http://${ENV.host}:${ENV.port}/assets/gmail.png`,
    oauthName: "google",
    actions: [
      {
        id: 1,
        actionName: "New mail from",
        actionParamName: "Sender email",
        paramFormat: FORMAT.email,
        description: "You've got a new mail from an address",
        fct: GmailAction.newMailFrom,
        availableInjectParams: ["content", "subject"],
      },
    ],
    reactions: [],
  },
];
