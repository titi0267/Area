import { Service } from "../types/areaServices/areaServices.types";
import * as YoutubeActions from "../area/youtube/youtube.action";
import * as YoutubeReactions from "../area/youtube/youtube.reaction";
import * as DiscordReaction from "../area/discord/discord.reaction";
import * as SpotifyAction from "../area/spotify/spotify.actions";
import * as SpotifyReaction from "../area/spotify/spotify.reactions";
import * as GithubAction from "../area/github/github.action";
import * as GithubReaction from "../area/github/github.reaction";
import * as GmailAction from "../area/gmail/gmail.action";
import * as WeatherAction from "../area/weather/weather.action";
import * as TimeAction from "../area/time/time.action";
import { FORMAT } from "./paramFormat";

export const SERVICES: Service[] = [
  {
    id: 1,
    serviceName: "Youtube",
    oauthName: "google",
    imageUrl: "assets/youtube.png",
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
    imageUrl: "assets/discord.png",
    backgroundColor: "#5865F2",
    oauthName: "discord",
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
    imageUrl: "assets/spotify.png",
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
    reactions: [
      {
        id: 1,
        reactionName: "Add a song to Area playlist",
        reactionParamName:
          "Name of the track you that you want to add in Area playlist",
        paramFormat: null,
        description:
          "You'll have a new song added into a playlist called Area (created if it doesn't exist)",
        fct: SpotifyReaction.addTrackToPlaylist,
      },
      {
        id: 2,
        reactionName: "Update your volume",
        reactionParamName: "Number to set volume to",
        paramFormat: null,
        description: "Change the volume of your device",
        fct: SpotifyReaction.updateVolume,
      },
      {
        id: 3,
        reactionName: "Start a music",
        reactionParamName: "Name of the music to play",
        paramFormat: null,
        description: "Starts playing the music",
        fct: SpotifyReaction.startMusic,
      },
      {
        id: 4,
        reactionName: "Put music into queue",
        reactionParamName: "Name of the music to put into queue",
        paramFormat: null,
        description: "Puts music into queue",
        fct: SpotifyReaction.addMusicToQueue,
      },
      {
        id: 5,
        reactionName: "Loop on music",
        reactionParamName: "",
        paramFormat: null,
        description: "Loop on your current track",
        fct: SpotifyReaction.repeatMusic,
      },
    ],
  },
  {
    id: 4,
    serviceName: "Github",
    imageUrl: "assets/github.png",
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
      {
        id: 2,
        actionName: "New issue",
        actionParamName: "",
        paramFormat: null,
        description: "New issue on one of your repository",
        fct: GithubAction.newIssue,
        availableInjectParams: ["title", "repositoryName"],
      },
      {
        id: 3,
        actionName: "New pull request",
        actionParamName: "Repository infos (format: /owner/repo)",
        paramFormat: FORMAT.githubPullRequestFormat,
        description: "New pull request on one of your repository",
        fct: GithubAction.newPullRequestOnRepository,
        availableInjectParams: ["title", "creator", "body"],
      },
    ],
    reactions: [
      {
        id: 1,
        reactionName: "Create an issue",
        reactionParamName: "Issue infos (format: /owner/repo/issueTitle)",
        paramFormat: FORMAT.githubIssueFormat,
        fct: GithubReaction.createGithubIssue,
        description:
          "Create an issue on a public repository or a private repository on which you belong",
      },
    ],
  },
  {
    id: 5,
    serviceName: "Gmail",
    backgroundColor: "#DE5145",
    imageUrl: "assets/gmail.png",
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
  {
    id: 6,
    serviceName: "Weather",
    backgroundColor: "#00BFFF",
    imageUrl: "assets/weather.png",
    oauthName: null,
    actions: [
      {
        id: 1,
        actionName: "Weather is clear",
        actionParamName: "City name",
        paramFormat: null,
        description: "The weather became clear in your city",
        fct: WeatherAction.weatherBecameClear,
        availableInjectParams: ["temperature", "clouds"],
      },
    ],
    reactions: [],
  },
  {
    id: 7,
    serviceName: "Time & Date",
    backgroundColor: "#000000",
    imageUrl: "assets/time.png",
    oauthName: null,
    actions: [
      {
        id: 1,
        actionName: "Every day at",
        actionParamName: "Time (format: 00:00)",
        paramFormat: FORMAT.time,
        description: "Do something everyday at a precise time",
        fct: TimeAction.everyDayAt,
        availableInjectParams: [],
      },
    ],
    reactions: [],
  },
];
