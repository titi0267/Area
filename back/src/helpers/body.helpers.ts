import httpStatus from "http-status";

import ClientError from "../error";
import {
  RawRegisterBody,
  FormatedRegisterBody,
} from "../types/body/userRequestBody.types";
import {
  RawAreaBody,
  FormatedAreaBody,
} from "../types/body/areaRequestBody.types";
import areaRoute from "../routes/area.route";
import userRoute from "../routes/user.route";
import userService from "../services/user.service";
import { UserService } from "../services";

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

export { checkRegisterBody, checkAreaBody };
