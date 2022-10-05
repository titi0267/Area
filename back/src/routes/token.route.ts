import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";
import { FastifyPluginDoneFunction } from "../types/global.types";

import { RawTokenBody } from "../types/body/tokenRequestBody.types";
import { TokenService } from "../services/";
import httpStatus from "http-status";
import * as BodyHelper from "../helpers/body.helpers";

type TokenRequest = FastifyRequest<{
  Body: RawTokenBody;
}>;

export default (
  instance: FastifyInstance,
  _opts: FastifyPluginOptions,
  done: FastifyPluginDoneFunction,
): void => {
  instance.put("/", async (req: TokenRequest, res: FastifyReply) => {
    const formatedBody = BodyHelper.checkTokenBody(req.body);

    const token = await TokenService.updateToken(
      formatedBody.discordToken,
      formatedBody.githubToken,
      formatedBody.spotifyToken,
      formatedBody.trelloToken,
      formatedBody.twitterToken,
      formatedBody.youtubeToken,
      formatedBody.userId,
    );
    res.status(httpStatus.OK).send(token);
  });
  done();
};

export const autoPrefix = "/token";
