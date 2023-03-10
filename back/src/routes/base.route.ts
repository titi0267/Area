import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";

import { FastifyPluginDoneFunction } from "../types/global.types";
import httpStatus from "http-status";
import { SERVICES } from "../constants/serviceList";

export default (
  instance: FastifyInstance,
  _opts: FastifyPluginOptions,
  done: FastifyPluginDoneFunction,
): void => {
  instance.get(
    "/about.json",
    async (req: FastifyRequest, res: FastifyReply) => {
      const about = {
        client: {
          host: req.ip,
        },
        server: {
          current_time: new Date().getTime(),
          services: SERVICES.map(service => {
            return {
              id: service.id,
              name: service.serviceName,
              imageUrl: service.imageUrl,
              backgroundColor: service.backgroundColor,
              oauthName: service.oauthName,
              actions: service.actions.map(action => {
                return {
                  id: action.id,
                  name: action.actionName,
                  actionParamName: action.actionParamName,
                  description: action.description,
                  availableInjectParams: action.availableInjectParams,
                };
              }),
              reactions: service.reactions.map(reaction => {
                return {
                  id: reaction.id,
                  name: reaction.reactionName,
                  reactionParamName: reaction.reactionParamName,
                  description: reaction.description,
                };
              }),
            };
          }),
        },
      };
      res.status(httpStatus.OK).send(about);
    },
  );

  done();
};

export const autoPrefix = "/";
