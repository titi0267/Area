import { Service } from "../types/areaServices/areaServices.types";
import * as YoutubeActions from "../area/youtube/youtube.action";
import * as YoutubeReactions from "../area/youtube/youtube.reaction";
import * as TwitterReaction from "../area/twitter/twitter.reaction";
import * as DiscordReaction from "../area/discord/discord.reaction";
import * as SpotifyAction from "../area/spotify/spotify.actions";
import * as GithubAction from "../area/github/github.action";

export const SERVICES: Service[] = [
  {
    id: 1,
    serviceName: "Youtube",
    oauthName: "google",
    imageUrl: "https://www.iconsdb.com/icons/preview/white/youtube-6-xxl.png",
    backgroundColor: "#FF0000",
    actions: [
      {
        id: 1,
        actionName: "NewVideoUploaded",
        actionParamName: "Channel Name",
        fct: YoutubeActions.checkUploadedVideo,
        availableInjectParams: ["name", "channelName"],
      },
      {
        id: 2,
        actionName: "NewLikeOnAVideo",
        actionParamName: "Video Id",
        fct: YoutubeActions.checkVideoLike,
        availableInjectParams: ["like", "viewCount"],
      },
      {
        id: 3,
        actionName: "New Liked Video",
        actionParamName: "",
        fct: YoutubeActions.checkNewVideoLiked,
        availableInjectParams: ["channel", "title"],
      },
    ],
    reactions: [
      {
        id: 1,
        reactionName: "Like a video",
        reactionParamName: "Video to like",
        fct: YoutubeReactions.likeVideo,
      },
      {
        id: 2,
        reactionName: "Dislike a video",
        reactionParamName: "Video to dislike",
        fct: YoutubeReactions.dislikeVideo,
      },
    ],
  },
  {
    id: 2,
    serviceName: "Twitter",
    imageUrl: "https://www.iconsdb.com/icons/preview/white/twitter-xxl.png",
    backgroundColor: "#1DA1F2",
    actions: [],
    oauthName: null,
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
    imageUrl: "https://www.iconsdb.com/icons/preview/white/discord-2-xxl.png",
    backgroundColor: "#5865F2",
    oauthName: null,
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
    imageUrl: "https://www.iconsdb.com/icons/preview/white/spotify-xxl.png",
    backgroundColor: "#1DB954",
    oauthName: "spotify",
    actions: [
      {
        id: 1,
        actionName: "Skip to next song",
        actionParamName: "Skip song",
        fct: SpotifyAction.checkMusicSkip,
        availableInjectParams: ["songName"],
      },
      {
        id: 2,
        actionName: "Get liked track",
        actionParamName: "Liked track",
        fct: SpotifyAction.checkIsMusicLiked,
        availableInjectParams: ["songName", "artists"],
      },
    ],
    reactions: [],
  },
  {
    id: 5,
    serviceName: "Github",
    imageUrl: "https://www.iconsdb.com/icons/preview/white/github-9-xxl.png",
    backgroundColor: "#000000",
    oauthName: "github",
    actions: [
      {
        id: 1,
        actionName: "New follower",
        actionParamName: "",
        fct: GithubAction.checkNewFollowingUser,
        availableInjectParams: ["lastFollowingUserName"],
      },
    ],
    reactions: [],
  },
];
