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

  const doesActionExist = SERVICES.find(service =>
    service.actions.find(action => action.id === actionId),
  );

  const doesReactionServiceExist = SERVICES.find(
    service => service.id === reactionServiceId,
  );

  const doesReactionExist = SERVICES.find(service =>
    service.reactions.find(reaction => reaction.id === reactionId),
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

export { rejectInvalidArea, getActionFct, getReactionFct };
