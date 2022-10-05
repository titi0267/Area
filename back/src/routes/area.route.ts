import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";

import { FastifyPluginDoneFunction } from "../types/global.types";
import { AreaService } from "../services";
import httpStatus from "http-status";
import { RawAreaBody } from "../types/body/areaRequestBody.types";
import * as BodyHelper from "../helpers/body.helpers";
import { format } from "path";

type AreaRequest = FastifyRequest<{
  Body: RawAreaBody;
}>;

export default (
  instance: FastifyInstance,
  _opts: FastifyPluginOptions,
  done: FastifyPluginDoneFunction,
): void => {
  instance.post("/", async (req: AreaRequest, res: FastifyReply) => {
    const formatedBody = BodyHelper.checkAreaBody(req.body);
    const areas = await AreaService.createArea(
      formatedBody.actionService,
      formatedBody.action,
      formatedBody.actionParam,
      formatedBody.reactionService,
      formatedBody.reaction,
      formatedBody.reactionParam,
      formatedBody.userId,
    );
    res.status(httpStatus.OK).send(areas);
  });

  done();
};

export const autoPrefix = "/areas";
