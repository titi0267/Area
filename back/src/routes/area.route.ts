import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";

import { FastifyPluginDoneFunction } from "../types/global.types";
import { AreaService } from "../services";
import httpStatus from "http-status";
import { AreaBody, EditAreaBody } from "../types/body/areaRequestBody.types";
import * as SecurityHelper from "../helpers/security.helper";
import * as ErrorHelper from "../helpers/error.helpers";
import authentificationMiddleware from "../middlewares/authentification.middleware";
import {
  areaBodyValidator,
  editAreaBodyValidator,
  deleteAreaParamValidator,
  getUserAreaByIdParamValidator,
} from "../schema/area.schema";
import { IdParam } from "../types/areaServices/areaServices.types";

type AreaRequest = FastifyRequest<{
  Body: AreaBody;
}>;

type EditAreaRequest = FastifyRequest<{
  Body: EditAreaBody;
}>;

type DeleteAreaRequest = FastifyRequest<{
  Params: IdParam;
}>;

type GetUserAreaById = FastifyRequest<{
  Params: IdParam;
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
      if (!areaBodyValidator(req.body)) ErrorHelper.throwBodyError();

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

  instance.get(
    "/:id",
    { onRequest: [authentificationMiddleware()] },
    async (req: GetUserAreaById, res: FastifyReply) => {
      const userInfos = SecurityHelper.getUserInfos(req);
      if (!getUserAreaByIdParamValidator(req.params))
        ErrorHelper.throwBodyError();

      const areaId = ErrorHelper.verifyIdParamIsNumber(req.params.id);

      const area = await AreaService.getUserAreaById(userInfos.id, areaId);
      res.status(httpStatus.OK).send(area);
    },
  );

  instance.put(
    "/",
    { onRequest: [authentificationMiddleware()] },
    async (req: EditAreaRequest, res: FastifyReply) => {
      const userInfos = SecurityHelper.getUserInfos(req);
      if (!editAreaBodyValidator(req.body)) ErrorHelper.throwBodyError();

      const area = await AreaService.editArea(
        userInfos.id,
        req.body.areaId,
        req.body.enabled,
        req.body.actionParam,
        req.body.reactionParam,
      );

      res.status(httpStatus.OK).send(area);
    },
  );

  instance.delete(
    "/:id",
    { onRequest: [authentificationMiddleware()] },
    async (req: DeleteAreaRequest, res: FastifyReply) => {
      const userInfos = SecurityHelper.getUserInfos(req);
      if (!deleteAreaParamValidator(req.params)) ErrorHelper.throwBodyError();

      const areaId = ErrorHelper.verifyIdParamIsNumber(req.params.id);

      const area = await AreaService.removeUserArea(userInfos.id, areaId);

      res.status(httpStatus.OK).send(area);
    },
  );

  done();
};

export const autoPrefix = "/areas";
