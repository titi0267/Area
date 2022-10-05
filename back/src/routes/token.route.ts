import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";
import { FastifyPluginDoneFunction } from "../types/global.types";

import { RawTokenBody } from "../types/body/tokenRequestBody.types";
import { TokenService } from "../services/";
import httpStatus from "http-status";

type TokenRequest = FastifyRequest<{
  Body: RawTokenBody;
}>;

export default (
  instance: FastifyInstance,
  _opts: FastifyPluginOptions,
  done: FastifyPluginDoneFunction,
): void => {
  instance.put("/", async (req: TokenRequest, res: FastifyReply) => {
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
