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
    !body.action ||
    !body.actionParam ||
    !body.actionService ||
    !body.reactionService ||
    !body.reaction ||
    !body.reactionParam ||
    !body.userId
  ) {
    throw new ClientError({
      name: "Missing element",
      message: "One of the mandatory field was not provided",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }
  return {
    actionService: body.actionService,
    action: body.action,
    actionParam: body.actionParam,
    reactionService: body.reactionService,
    reaction: body.reaction,
    reactionParam: body.reactionParam,
    userId: body.userId,
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
