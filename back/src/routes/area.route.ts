import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";

import { FastifyPluginDoneFunction } from "../types/global.types";
import { AreaService } from "../services";
import httpStatus from "http-status";
import { AreaBody } from "../types/body/areaRequestBody.types";
import * as SecurityHelper from "../helpers/security.helper";
import authentificationMiddleware from "../middlewares/authentification.middleware";
import { areaBodyValidator } from "../schema/area.schema";
import { throwBodyError } from "../helpers/error.helpers";

type AreaRequest = FastifyRequest<{
  Body: AreaBody;
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
      if (!areaBodyValidator(req.body)) throwBodyError();

      const areas = await AreaService.createArea(
        req.body.actionServiceId,
        req.body.actionId,
        req.body.actionParam,
        req.body.reactionServiceId,
        req.body.reactionId,
        req.body.reactionParam,
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
