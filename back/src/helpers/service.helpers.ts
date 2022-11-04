import { createOAuthUserAuth } from "@octokit/auth-oauth-user";
import { OAuth2Client } from "google-auth-library";
import { google } from "googleapis";
import httpStatus from "http-status";
import { Octokit } from "octokit";
import SpotifyWebApi from "spotify-web-api-node";
import { FORMAT } from "../constants/paramFormat";
import { SERVICES } from "../constants/serviceList";
import ENV from "../env";
import ClientError from "../error";
import { TokenService } from "../services";

const rejectInvalidArea = (
  actionServiceId: number,
  actionId: number,
  actionParam: string,
  reactionServiceId: number,
  reactionId: number,
  reactionParam: string,
) => {
  const doesActionServiceExist = SERVICES.find(
    service => service.id === actionServiceId,
  );

  const doesActionExist = doesActionServiceExist?.actions.find(
    action => action.id === actionId,
  );

  const doesReactionServiceExist = SERVICES.find(
    service => service.id === reactionServiceId,
  );

  const doesReactionExist = doesReactionServiceExist?.reactions.find(
    reaction => reaction.id === reactionId,
  );

  if (
    doesActionExist?.paramFormat &&
    !actionParam.match(doesActionExist.paramFormat)
  ) {
    throw new ClientError({
      name: "Invalid Param",
      message: "Action param does not match the require format",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  if (
    doesReactionExist?.paramFormat &&
    !reactionParam.match(doesReactionExist.paramFormat)
  ) {
    throw new ClientError({
      name: "Invalid Param",
      message: "Reaction param does not match the require format",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  if (
    !doesActionExist ||
    !doesActionServiceExist ||
    !doesReactionServiceExist ||
    !doesReactionExist
  ) {
    throw new ClientError({
      name: "Invalid id",
      message: "Provided action or reaction id not found",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }
};

const getActionFct = (actionServiceId: number, actionId: number) => {
  const actionService = SERVICES.find(
    service => service.id === actionServiceId,
  );

  if (actionService === undefined) return null;
  const action = actionService.actions.find(action => action.id === actionId);

  return action?.fct || null;
};

const getReactionFct = (reactionServiceId: number, reactionId: number) => {
  const reactionService = SERVICES.find(
    service => service.id === reactionServiceId,
  );

  if (reactionService === undefined) return null;
  const reaction = reactionService.reactions.find(
    reaction => reaction.id === reactionId,
  );

  return reaction?.fct || null;
};

const checkActionFormat = (
  actionServiceId: number,
  actionId: number,
  param: string,
): boolean => {
  const doesActionServiceExist = SERVICES.find(
    service => service.id === actionServiceId,
  );

  const doesActionExist = doesActionServiceExist?.actions.find(
    action => action.id === actionId,
  );

  if (!doesActionServiceExist || !doesActionExist) return false;

  if (!doesActionExist.paramFormat) return true;

  if (!param.match(doesActionExist.paramFormat)) return false;

  return true;
};

const checkReactionFormat = (
  reactionServiceId: number,
  reactionId: number,
  param: string,
): boolean => {
  const doesReactionServiceExist = SERVICES.find(
    service => service.id === reactionServiceId,
  );

  const doesReactionExist = doesReactionServiceExist?.reactions.find(
    reaction => reaction.id === reactionId,
  );

  if (!doesReactionServiceExist || !doesReactionExist) return false;

  if (!doesReactionExist.paramFormat) return true;

  if (!param.match(doesReactionExist.paramFormat)) return false;

  return true;
};

const getYoutubeVideoId = (url: string) => {
  let regex = FORMAT.youtubeVideoUrl;

  const matches = url.match(regex);

  if (!matches || !matches[3]) return null;

  return matches[3];
};

const getYoutubeChannelName = (url: string) => {
  let regex = FORMAT.youtubeChannelUrl;

  const matches = url.match(regex);

  if (!matches || !matches[1]) return null;

  return matches[1];
};

const getGithubIssueParams = (reactionParam: string) => {
  let regex = FORMAT.githubIssueFormat;

  const matches = reactionParam.match(regex);

  if (!matches || matches.length < 4) return null;

  return { owner: matches[1], repo: matches[2], title: matches[3] };
};

const injectParamInReaction = <T extends Object>(
  reactionParam: string,
  param: T,
): string => {
  const matchParamRegex = /(%(\w+)%)/g;

  const matches = reactionParam.matchAll(matchParamRegex);

  for (const match of matches) {
    if (!match || !match[2]) continue;

    const key = match[2];

    if (!param.hasOwnProperty(key)) continue;
    const value = String(param[key as keyof T]);

    reactionParam = reactionParam.replace("%" + key + "%", value);
  }

  return reactionParam;
};

const getGoogleOauthClient = async (
  userId: number,
): Promise<OAuth2Client | null> => {
  const refreshToken = await TokenService.getGoogleToken(userId);

  if (!refreshToken) return null;

  const oAuth2Client = new google.auth.OAuth2(
    ENV.googleClientId,
    ENV.googleClientSecret,
    ENV.googleRedirectUrl,
  );

  oAuth2Client.setCredentials({ refresh_token: refreshToken });

  return oAuth2Client;
};

const getGithubClient = async (userId: number) => {
  const token = await TokenService.getGithubToken(userId);

  if (!token) return null;

  const octokit = new Octokit({
    authStrategy: createOAuthUserAuth,
    auth: {
      clientId: ENV.githubClientId,
      clientSecret: ENV.githubClientSecret,
      token,
      clientType: "oauth-app",
    },
  });

  return octokit;
};

const getSpotifyClient = async (userId: number) => {
  const token = await TokenService.getSpotifyToken(userId);

  if (!token) return null;

  const spotifyApi = new SpotifyWebApi({
    clientId: ENV.spotifyClientId,
    clientSecret: ENV.spotifyClientSecret,
  });

  return { client: spotifyApi, token };
};

export {
  rejectInvalidArea,
  checkActionFormat,
  checkReactionFormat,
  getActionFct,
  getReactionFct,
  getYoutubeVideoId,
  getYoutubeChannelName,
  injectParamInReaction,
  getGoogleOauthClient,
  getGithubClient,
  getSpotifyClient,
  getGithubIssueParams,
};
