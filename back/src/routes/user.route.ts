import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";
import httpStatus from "http-status";
import ClientError from "../error";
import { UserService } from "../services";

import { FastifyPluginDoneFunction } from "../types/global.types";

type RegisterRequest = FastifyRequest<{
  Body: {
    firstName: string | undefined;
    lastName: string | undefined;
    email: string | undefined;
    password: string | undefined;
  };
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
    if (
      !req.body.firstName ||
      !req.body.lastName ||
      !req.body.email ||
      !req.body.password
    ) {
      throw new ClientError({
        name: "Missing element",
        message: "One of the mandatory field was not provided",
        level: "warm",
        status: httpStatus.BAD_REQUEST,
      });
    }

    const token = await UserService.createUser(
      req.body.firstName,
      req.body.lastName,
      req.body.email,
      req.body.password,
    );

    res.status(httpStatus.OK).send(token);
  });

  done();
};

export const autoPrefix = "/users";
