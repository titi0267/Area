import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";
import httpStatus from "http-status";

import { AreaService, UserService } from "../services";
import * as SecurityHelper from "../helpers/security.helper";
import { FastifyPluginDoneFunction } from "../types/global.types";
import { LoginBody, RegisterBody } from "../types/body/userRequestBody.types";
import authentificationMiddleware from "../middlewares/authentification.middleware";
import { loginBodyValidator, registerBodyValidator } from "../shema/user.shema";
import { throwBodyError } from "../helpers/error.helpers";

type RegisterRequest = FastifyRequest<{
  Body: RegisterBody;
}>;

type LoginRequest = FastifyRequest<{
  Body: LoginBody;
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
    if (!registerBodyValidator(req.body)) throwBodyError();

    const token = await UserService.createUser(
      req.body.firstName,
      req.body.lastName,
      req.body.email,
      req.body.password,
    );

    res.status(httpStatus.OK).send(token);
  });

  instance.post("/login", async (req: LoginRequest, res: FastifyReply) => {
    if (!loginBodyValidator(req.body)) throwBodyError();

    const token = await UserService.loginUser(
      req.body.email,
      req.body.password,
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
