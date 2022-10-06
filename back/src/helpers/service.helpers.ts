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
    service.reactions.find(reaction => reaction.id === actionId),
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

export { rejectInvalidArea };
