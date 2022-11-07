import {
  FastifyInstance,
  FastifyReply,
  FastifyRequest,
  FastifyPluginOptions,
} from "fastify";
import httpStatus from "http-status";

import authentificationMiddleware from "../middlewares/authentification.middleware";
import { FastifyPluginDoneFunction } from "../types/global.types";
import * as SecurityHelper from "../helpers/security.helper";
import { removeTokenBodyValidator } from "../schema/token.schema";
import * as ErrorHelper from "../helpers/error.helpers";
import { RemoveTokenBody } from "../types/body/tokenRequestBody.types";
import { TokenService } from "../services";

type RemoveTokenRequest = FastifyRequest<{
  Body: RemoveTokenBody;
}>;

export default (
  instance: FastifyInstance,
  _opts: FastifyPluginOptions,
  done: FastifyPluginDoneFunction,
): void => {
  instance.put(
    "/delete",
    { onRequest: [authentificationMiddleware()] },
    async (req: RemoveTokenRequest, res: FastifyReply) => {
      const userInfos = SecurityHelper.getUserInfos(req);
      if (!removeTokenBodyValidator(req.body)) ErrorHelper.throwBodyError();

      const tokens = await TokenService.removeUserToken(
        userInfos.id,
        req.body.discordGuildId,
        req.body.discordToken,
        req.body.googleToken,
        req.body.spotifyToken,
        req.body.githubToken,
      );

      res.status(httpStatus.OK).send(tokens);
    },
  );
  done();
};

export const autoPrefix = "/tokens";
