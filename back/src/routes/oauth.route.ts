import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";
import httpStatus from "http-status";

import { FastifyPluginDoneFunction } from "../types/global.types";
import { googleOauthQueryValidator } from "../shema/oauth.schema";
import { GoogleOauthQuery } from "../types/query/oauthRequestQuery.types";
import * as ErrorHelper from "../helpers/error.helpers";
import { google } from "googleapis";
import ENV from "../env";

type GoogleOauthRequest = FastifyRequest<{
  Querystring: GoogleOauthQuery;
}>;

export default (
  instance: FastifyInstance,
  _opts: FastifyPluginOptions,
  done: FastifyPluginDoneFunction,
): void => {
  instance.get(
    "/google",
    async (req: GoogleOauthRequest, res: FastifyReply) => {
      if (!googleOauthQueryValidator(req.query)) ErrorHelper.throwBodyError();
      const code = req.query.code;

      const oauthClient = new google.auth.OAuth2(
        ENV.googleClientId,
        ENV.googleClientSecret,
        ENV.googleRedirectUrl,
      );

      const tokens = (await oauthClient.getToken(code)).tokens;

      res.redirect("http://localhost:8081/");
    },
  );

  done();
};

export const autoPrefix = "/oauth";
