import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";

import { FastifyPluginDoneFunction } from "../types/global.types";
import { AreaService } from "../services";
import httpStatus from "http-status";
import { RawAreaBody } from "../types/body/areaRequestBody.types";
import * as BodyHelper from "../helpers/body.helpers";
import * as SecurityHelper from "../helpers/security.helper";
import authentificationMiddleware from "../middlewares/authentification.middleware";

type AreaRequest = FastifyRequest<{
  Body: RawAreaBody;
}>;

export default (
  instance: FastifyInstance,
  _opts: FastifyPluginOptions,
  done: FastifyPluginDoneFunction,
): void => {
  instance.post(
    "/",
    { onRequest: [authentificationMiddleware()] },
    async (req: AreaRequest, res: FastifyReply) => {
      const userInfos = SecurityHelper.getUserInfos(req);
      const formatedBody = BodyHelper.checkAreaBody(req.body);
      const areas = await AreaService.createArea(
        formatedBody.actionServiceId,
        formatedBody.actionId,
        formatedBody.actionParam,
        formatedBody.reactionServiceId,
        formatedBody.reactionId,
        formatedBody.reactionParam,
        userInfos.id,
      );
      res.status(httpStatus.OK).send(areas);
    },
  );

  instance.get("/", async (req: FastifyRequest, res: FastifyReply) => {
    const areas = await AreaService.getAllArea();

    res.status(httpStatus.OK).send(areas);
  });

  done();
};

export const autoPrefix = "/areas";
