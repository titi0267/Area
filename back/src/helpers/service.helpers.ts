import httpStatus from "http-status";
import { SERVICES } from "../constants/serviceList";
import ClientError from "../error";

const rejectInvalidArea = (
  actionServiceId: number,
  actionId: number,
  reactionServiceId: number,
  reactionId: number,
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

  if (!matches) return null;

  return matches[3] || null;
};

const getYoutubeChannelName = (url: string) => {
  let regex = /https:\/+www.youtube.com\/user\/(\w+)/;

  const matches = url.match(regex);

  if (!matches) return null;

  return matches[1] || null;
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

export {
  rejectInvalidArea,
  getActionFct,
  getReactionFct,
  getYoutubeVideoId,
  getYoutubeChannelName,
  injectParamInReaction,
};
