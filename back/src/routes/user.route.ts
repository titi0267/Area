import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";
import httpStatus from "http-status";

import { AreaService, UserService } from "../services";
import * as BodyHelper from "../helpers/body.helpers";
import * as SecurityHelper from "../helpers/security.helper";
import { FastifyPluginDoneFunction } from "../types/global.types";
import {
  RawLoginBody,
  RawRegisterBody,
} from "../types/body/userRequestBody.types";
import authentificationMiddleware from "../middlewares/authentification.middleware";

type RegisterRequest = FastifyRequest<{
  Body: RawRegisterBody;
}>;

type LoginRequest = FastifyRequest<{
  Body: RawLoginBody;
}>;

export default (
  instance: FastifyInstance,
  _opts: FastifyPluginOptions,
  done: FastifyPluginDoneFunction,
): void => {
  instance.get("/", async (req: FastifyRequest, res: FastifyReply) => {
    const users = await UserService.getAllUsers();

    res.status(httpStatus.OK).send(users);
  });

  instance.post("/", async (req: RegisterRequest, res: FastifyReply) => {
    const formatedBody = BodyHelper.checkRegisterBody(req.body);

    const token = await UserService.createUser(
      formatedBody.firstName,
      formatedBody.lastName,
      formatedBody.email,
      formatedBody.password,
    );

    res.status(httpStatus.OK).send(token);
  });

  instance.post("/login", async (req: LoginRequest, res: FastifyReply) => {
    const formatedBody = BodyHelper.checkLoginBody(req.body);

    const token = await UserService.loginUser(
      formatedBody.email,
      formatedBody.password,
    );

    res.status(httpStatus.OK).send(token);
  });

  instance.get(
    "/areas",
    { onRequest: [authentificationMiddleware()] },
    async (req: FastifyRequest, res: FastifyReply) => {
      const userInfos = SecurityHelper.getUserInfos(req);
      const areas = await AreaService.getAreasByUserId(userInfos.id);

      res.status(httpStatus.OK).send(areas);
    },
  );

  done();
};

export const autoPrefix = "/users";
