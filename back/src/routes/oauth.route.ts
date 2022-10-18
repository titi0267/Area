import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";

import { FastifyPluginDoneFunction } from "../types/global.types";
import {
  googleOauthQueryValidator,
  spotifyOauthQueryValidator,
} from "../shema/oauth.schema";
import {
  GoogleOauthQuery,
  SpotifyOauthQuery,
} from "../types/query/oauthRequestQuery.types";
import * as ErrorHelper from "../helpers/error.helpers";
import { google } from "googleapis";
import ENV from "../env";
import SpotifyWebApi from "spotify-web-api-node";

type GoogleOauthRequest = FastifyRequest<{
  Querystring: GoogleOauthQuery;
}>;

type SpotifyOauthRequest = FastifyRequest<{
  Querystring: SpotifyOauthQuery;
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
  instance.get(
    "/spotify",
    async (req: SpotifyOauthRequest, res: FastifyReply) => {
      if (!spotifyOauthQueryValidator(req.query)) ErrorHelper.throwBodyError();
      const code = req.query.code;
      var spotifyApi = new SpotifyWebApi({
        clientId: ENV.spotifyClientId,
        clientSecret: ENV.spotifyClientSecret,
        redirectUri: ENV.spotifyRedirectUrl,
      });
      const tokens = (await spotifyApi.authorizationCodeGrant(code)).body;

      console.log(tokens);

      res.redirect("http://localhost:8081/");
    },
  );

  done();
};

export const autoPrefix = "/oauth";
