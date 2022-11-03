import {
  FastifyInstance,
  FastifyPluginOptions,
  FastifyReply,
  FastifyRequest,
} from "fastify";
import fs from "fs";
import httpStatus from "http-status";
import { throwBodyError } from "../helpers/error.helpers";
import { getImageParamValidator } from "../schema/assets.schema";

import { FastifyPluginDoneFunction, ImageParam } from "../types/global.types";

type GetImageRequest = FastifyRequest<{
  Params: ImageParam;
}>;

export default (
  instance: FastifyInstance,
  _opts: FastifyPluginOptions,
  done: FastifyPluginDoneFunction,
): void => {
  instance.get("/:image", async (req: GetImageRequest, res: FastifyReply) => {
    if (!getImageParamValidator(req.params)) throwBodyError();

    try {
      const buffer = fs.readFileSync(`./assets/${req.params.image}`);

      res.type("image/png");
      return res.send(buffer);
    } catch (e) {
      res.status(httpStatus.BAD_REQUEST).send(e);
    }
  });
  done();
};

export const autoPrefix = "/assets";
