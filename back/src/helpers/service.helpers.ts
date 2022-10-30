import { OAuth2Client } from "google-auth-library";
import { google } from "googleapis";
import httpStatus from "http-status";
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

const getYoutubeVideoId = (url: string) => {
  let regex = /(\w+:\/+[\w+.]+\/)(watch\?v=)(\w+)/;

  const matches = url.match(regex);

  if (!matches || !matches[3]) return null;

  return matches[3];
};

const getYoutubeChannelName = (url: string) => {
  let regex = /https:\/+www.youtube.com\/user\/(\w+)/;

  const matches = url.match(regex);

  if (!matches || !matches[1]) return null;

  return matches[1];
};

const injectParamInReaction = <T extends Object>(
  reactionParam: string,
  param: T,
): string => {
  const matchParamRegex = /(%(\w+)%)/;
  const replaceRegex = /(%\w+%)/;

  const matches = reactionParam.match(matchParamRegex);

  if (!matches || !matches[2]) return reactionParam;
  const key = matches[2];

  if (!param.hasOwnProperty(key)) return reactionParam;

  const value = String(param[key as keyof T]);

  return reactionParam.replace(replaceRegex, value);
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

export {
  rejectInvalidArea,
  getActionFct,
  getReactionFct,
  getYoutubeVideoId,
  getYoutubeChannelName,
  injectParamInReaction,
  getGoogleOauthClient,
};
