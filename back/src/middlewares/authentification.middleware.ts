import { FastifyRequest, FastifyReply, HookHandlerDoneFunction } from "fastify";
import HttpStatus from "http-status";

import * as SecurityMiddleware from "../helpers/security.helper";
import ClientError from "../error";

export default () => {
  return (
    req: FastifyRequest,
    _reply: FastifyReply,
    next: HookHandlerDoneFunction,
  ): void => {
    if (req.headers["authorization"]) {
      try {
        const decodedToken = SecurityMiddleware.decodeToken(
          req.headers["authorization"],
        );

        req.user = {
          id: decodedToken.id,
          email: decodedToken.email,
          role: decodedToken.role,
        };
        next();
      } catch {
        next(
          new ClientError({
            name: "ERROR_AUTHENTICATION",
            level: "warm",
            status: HttpStatus.UNAUTHORIZED,
            message: "Invalid token",
          }),
        );
      }
    } else {
      next(
        new ClientError({
          name: "ERROR_AUTHENTICATION",
          level: "warm",
          status: HttpStatus.UNAUTHORIZED,
          message: "Invalid token",
        }),
      );
    }
  };
};
