import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";

import { FastifyPluginDoneFunction } from "../types/global.types";
import {
  googleOauthQueryValidator,
  spotifyOauthQueryValidator,
} from "../schema/oauth.schema";
import {
  GoogleOauthBody,
  SpotifyOauthBody,
} from "../types/body/oauthRequestBody.types";
import * as SecurityHelper from "../helpers/security.helper";
import * as ErrorHelper from "../helpers/error.helpers";
import { google } from "googleapis";
import ENV from "../env";
import SpotifyWebApi from "spotify-web-api-node";
import authentificationMiddleware from "../middlewares/authentification.middleware";
import { TokenService } from "../services";
import httpStatus from "http-status";

type GoogleOauthRequest = FastifyRequest<{
  Body: GoogleOauthBody;
}>;

type SpotifyOauthRequest = FastifyRequest<{
  Body: SpotifyOauthBody;
}>;

export default (
  instance: FastifyInstance,
  _opts: FastifyPluginOptions,
  done: FastifyPluginDoneFunction,
): void => {
  instance.post(
    "/google",
    { onRequest: [authentificationMiddleware()] },
    async (req: GoogleOauthRequest, res: FastifyReply) => {
      if (!googleOauthQueryValidator(req.body)) ErrorHelper.throwBodyError();

      const userInfos = SecurityHelper.getUserInfos(req);

      const code = req.body.code;

      const oauthClient = new google.auth.OAuth2(
        ENV.googleClientId,
        ENV.googleClientSecret,
        ENV.googleRedirectUrl,
      );

      const tokens = (await oauthClient.getToken(code)).tokens;

      const tokenTable = await TokenService.setGoogleToken(
        userInfos.id,
        tokens.refresh_token,
      );

      res.status(httpStatus.OK).send(tokenTable);
    },
  );

  instance.post(
    "/spotify",
    { onRequest: [authentificationMiddleware()] },
    async (req: SpotifyOauthRequest, res: FastifyReply) => {
      if (!spotifyOauthQueryValidator(req.body)) ErrorHelper.throwBodyError();

      const userInfos = SecurityHelper.getUserInfos(req);

      const code = req.body.code;
      var spotifyApi = new SpotifyWebApi({
        clientId: ENV.spotifyClientId,
        clientSecret: ENV.spotifyClientSecret,
        redirectUri: ENV.spotifyRedirectUrl,
      });

      const tokens = (await spotifyApi.authorizationCodeGrant(code)).body;

      spotifyApi.setAccessToken(tokens.access_token);
      spotifyApi.setRefreshToken(tokens.refresh_token);

      const tokenTable = await TokenService.setSpotifyToken(
        userInfos.id,
        tokens.refresh_token,
      );

      res.status(httpStatus.OK).send(tokenTable);
    },
  );

  done();
};

export const autoPrefix = "/oauth";
