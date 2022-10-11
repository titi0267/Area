import httpStatus from "http-status";

import ClientError from "../error";
import {
  FormatedTokenBody,
  RawTokenBody,
} from "../types/body/tokenRequestBody.types";
import {
  RawRegisterBody,
  FormatedRegisterBody,
  RawLoginBody,
  FormatedLoginBody,
} from "../types/body/userRequestBody.types";
import {
  RawAreaBody,
  FormatedAreaBody,
} from "../types/body/areaRequestBody.types";
import * as ServiceHelper from "./service.helpers";

const checkRegisterBody = (body: RawRegisterBody): FormatedRegisterBody => {
  if (!body.firstName || !body.lastName || !body.email || !body.password) {
    throw new ClientError({
      name: "Missing element",
      message: "One of the mandatory field was not provided",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  return {
    firstName: body.firstName,
    lastName: body.lastName,
    email: body.email,
    password: body.password,
  };
};

const checkAreaBody = (body: RawAreaBody): FormatedAreaBody => {
  if (
    !body.actionId ||
    !body.actionParam ||
    !body.actionServiceId ||
    !body.reactionServiceId ||
    !body.reactionId ||
    !body.reactionParam
  ) {
    throw new ClientError({
      name: "Missing element",
      message: "One of the mandatory field was not provided",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const actionServiceId = parseInt(body.actionServiceId);
  const actionId = parseInt(body.actionId);
  const reactionServiceId = parseInt(body.reactionServiceId);
  const reactionId = parseInt(body.reactionId);

  if (
    Number.isNaN(actionServiceId) ||
    Number.isNaN(actionId) ||
    Number.isNaN(reactionId) ||
    Number.isNaN(reactionServiceId)
  ) {
    throw new ClientError({
      name: "Id not a int",
      message: "Id not a int",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  ServiceHelper.rejectInvalidArea(
    actionServiceId,
    actionId,
    reactionServiceId,
    reactionId,
  );

  return {
    actionServiceId,
    actionId,
    actionParam: body.actionParam,
    reactionServiceId,
    reactionId,
    reactionParam: body.reactionParam,
  };
};

const checkLoginBody = (body: RawLoginBody): FormatedLoginBody => {
  if (!body.email || !body.password) {
    throw new ClientError({
      name: "Missing element",
      message: "One of the mandatory field was not provided",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }
  return { email: body.email, password: body.password };
};

const checkTokenBody = (body: RawTokenBody): FormatedTokenBody => {
  if (!body.userId) {
    throw new ClientError({
      name: "Missing element",
      message: "One of the mandatory field was not provided",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }
  return {
    discordToken: body.discordToken,
    githubToken: body.githubToken,
    spotifyToken: body.spotifyToken,
    trelloToken: body.trelloToken,
    twitterToken: body.twitterToken,
    youtubeToken: body.youtubeToken,
    userId: body.userId,
  };
};

export { checkRegisterBody, checkLoginBody, checkAreaBody, checkTokenBody };
