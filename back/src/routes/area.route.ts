import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";

import { FastifyPluginDoneFunction } from "../types/global.types";
import { AreaService } from "../services";
import httpStatus from "http-status";

export default (
  instance: FastifyInstance,
  _opts: FastifyPluginOptions,
  done: FastifyPluginDoneFunction,
): void => {
  instance.get("/", async (req: FastifyRequest, res: FastifyReply) => {
    const areas = await AreaService.getAllArea();

    res.status(httpStatus.OK).send(areas);
  });

  done();
};

export const autoPrefix = "/areas";
