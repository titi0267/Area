import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";
import { FastifyPluginDoneFunction } from "../types/global.types";

import { TokenBody } from "../types/body/tokenRequestBody.types";
import * as ErrorHelper from "../helpers/error.helpers";
import { TokenService } from "../services/";
import httpStatus from "http-status";
import { tokenBodyValidator } from "../shema/token.shema";

type TokenRequest = FastifyRequest<{
  Body: TokenBody;
}>;

export default (
  instance: FastifyInstance,
  _opts: FastifyPluginOptions,
  done: FastifyPluginDoneFunction,
): void => {
  instance.put("/", async (req: TokenRequest, res: FastifyReply) => {
    if (!tokenBodyValidator(req.body)) ErrorHelper.throwBodyError();

    const token = await TokenService.updateToken(
      req.body.discordToken,
      req.body.githubToken,
      req.body.spotifyToken,
      req.body.trelloToken,
      req.body.twitterToken,
      req.body.youtubeToken,
      req.body.userId,
    );
    res.status(httpStatus.OK).send(token);
  });
  done();
};

export const autoPrefix = "/token";
