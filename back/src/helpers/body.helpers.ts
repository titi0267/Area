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

const checkLoginBody = (body: RawLoginBody): FormatedLoginBody => {
  if (!body.email || !body.password) {
    throw new ClientError({
      name: "Missing element",
      message: "One of the mandatory field was not provided",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  return {
    email: body.email,
    password: body.password,
  };
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

export { checkRegisterBody, checkLoginBody, checkTokenBody };
