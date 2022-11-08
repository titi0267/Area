import { FastifyInstance, FastifyReply, FastifyRequest } from "fastify";
import { FastifyPluginOptions } from "fastify/types/plugin";
import { OAuthApp } from "@octokit/oauth-app";
import { google } from "googleapis";
import SpotifyWebApi from "spotify-web-api-node";
import httpStatus from "http-status";
import DiscordOauthClient from "discord-oauth2";

import { FastifyPluginDoneFunction } from "../types/global.types";
import {
  googleOauthQueryValidator,
  spotifyOauthQueryValidator,
  githubOauthQueryValidator,
  discordOauthQueryValidator,
} from "../schema/oauth.schema";
import {
  GoogleOauthBody,
  SpotifyOauthBody,
  GithubOauthBody,
  DiscordOauthBody,
} from "../types/body/oauthRequestBody.types";
import * as SecurityHelper from "../helpers/security.helper";
import * as ErrorHelper from "../helpers/error.helpers";
import ENV from "../env";
import authentificationMiddleware from "../middlewares/authentification.middleware";
import { TokenService, UserService } from "../services";

type GoogleOauthRequest = FastifyRequest<{
  Body: GoogleOauthBody;
}>;

type SpotifyOauthRequest = FastifyRequest<{
  Body: SpotifyOauthBody;
}>;

type GithubOauthRequest = FastifyRequest<{
  Body: GithubOauthBody;
}>;

type DiscordOauthRequest = FastifyRequest<{
  Body: DiscordOauthBody;
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
    "/google/register",
    async (req: GoogleOauthRequest, res: FastifyReply) => {
      const code = req.body.code;

      const oauthClient = new google.auth.OAuth2(
        ENV.googleClientId,
        ENV.googleClientSecret,
        ENV.googleRedirectRegisterUrl,
      );

      const tokens = (await oauthClient.getToken(code)).tokens;

      oauthClient.setCredentials({ access_token: tokens.access_token });

      const userinfos = (
        await google.oauth2({ version: "v2", auth: oauthClient }).userinfo.get()
      ).data;

      const token = await UserService.connectOauthUser(
        userinfos.given_name || null,
        userinfos.family_name || null,
        userinfos.email || null,
        userinfos.id || null,
        tokens.refresh_token || null,
      );

      res.status(httpStatus.OK).send(token);
    },
  );

  instance.post(
    "/github",
    { onRequest: [authentificationMiddleware()] },
    async (req: GithubOauthRequest, res: FastifyReply) => {
      if (!githubOauthQueryValidator(req.body)) ErrorHelper.throwBodyError();

      const userInfos = SecurityHelper.getUserInfos(req);

      const app = new OAuthApp({
        clientType: "oauth-app",
        clientId: ENV.githubClientId,
        clientSecret: ENV.githubClientSecret,
      });

      const token = (await app.createToken({ code: req.body.code }))
        .authentication.token;

      const tokenTable = await TokenService.setGithubToken(userInfos.id, token);

      res.status(httpStatus.OK).send(tokenTable);
    },
  );

  instance.post(
    "/discord",
    { onRequest: [authentificationMiddleware()] },
    async (req: DiscordOauthRequest, res: FastifyReply) => {
      if (!discordOauthQueryValidator(req.body)) ErrorHelper.throwBodyError();

      const userInfos = SecurityHelper.getUserInfos(req);

      const oauth = new DiscordOauthClient();

      try {
        const getTokenRes = await oauth.tokenRequest({
          clientId: ENV.discordClientId,
          clientSecret: ENV.discordClientSecret,
          code: req.body.code,
          grantType: "authorization_code",
          redirectUri: ENV.discordRedirectUrl,
          scope: [
            "identify",
            "email",
            "guilds",
            "connections",
            "bot",
            "guilds.join",
          ].join(" "),
        });

        const tokenTable = await TokenService.setDiscordInfos(
          userInfos.id,
          getTokenRes.refresh_token,
          req.body.guild_id,
        );

        res.status(httpStatus.OK).send(tokenTable);
      } catch (e) {
        return res.send(e);
      }
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

  instance.get("/spotify/link", (req: FastifyRequest, res: FastifyReply) => {
    const rootUrl = "https://accounts.spotify.com/authorize";
    const options = {
      client_id: ENV.spotifyClientId,
      response_type: "code",
      redirect_uri: ENV.spotifyRedirectUrl,
      scope: [
        "user-read-private",
        "user-read-email",
        "user-modify-playback-state",
        "user-read-playback-state",
        "user-read-playback-position",
        "user-read-recently-played",
        "playlist-read-private",
        "user-read-currently-playing",
        "user-library-modify",
        "user-library-read",
        "playlist-modify-private",
        "playlist-modify-public",
      ].join(" "),
    };

    const qs = new URLSearchParams(options);

    res.status(httpStatus.OK).send(`${rootUrl}?${qs.toString()}`);
  });

  instance.get("/discord/link", (req: FastifyRequest, res: FastifyReply) => {
    const rootUrl = "https://discord.com/api/oauth2/authorize";

    const option = {
      client_id: ENV.discordClientId,
      response_type: "code",
      permissions: "8",
      redirect_uri: ENV.discordRedirectUrl,
      scope: [
        "identify",
        "email",
        "guilds",
        "connections",
        "bot",
        "guilds.join",
      ].join(" "),
    };

    const qs = new URLSearchParams(option);

    res.status(httpStatus.OK).send(`${rootUrl}?${qs.toString()}`);
  });

  instance.get("/google/link", (req: FastifyRequest, res: FastifyReply) => {
    const rootUrl = "https://accounts.google.com/o/oauth2/v2/auth";

    const options = {
      redirect_uri: ENV.googleRedirectUrl,
      client_id: ENV.googleClientId,
      access_type: "offline",
      response_type: "code",
      prompt: "consent",
      scope: [
        "https://www.googleapis.com/auth/userinfo.profile",
        "https://www.googleapis.com/auth/userinfo.email",
        "https://www.googleapis.com/auth/youtube.readonly",
        "https://www.googleapis.com/auth/youtube",
        "https://www.googleapis.com/auth/youtube.upload",
        "https://www.googleapis.com/auth/gmail.modify",
        "https://www.googleapis.com/auth/gmail.compose",
        "https://www.googleapis.com/auth/drive",
        "https://www.googleapis.com/auth/calendar",
      ].join(" "),
    };
    const qs = new URLSearchParams(options);

    res.status(httpStatus.OK).send(`${rootUrl}?${qs.toString()}`);
  });

  instance.get("/github/link", (req: FastifyRequest, res: FastifyReply) => {
    const rootUrl = "https://github.com/login/oauth/authorize";

    const options = {
      redirect_uri: ENV.githubRedirectUrl,
      client_id: ENV.githubClientId,
      scope: ["repo"].join(" "),
    };
    const qs = new URLSearchParams(options);

    res.status(httpStatus.OK).send(`${rootUrl}?${qs.toString()}`);
  });

  instance.get("/google/register", (req: FastifyRequest, res: FastifyReply) => {
    const rootUrl = "https://accounts.google.com/o/oauth2/v2/auth";

    const options = {
      redirect_uri: ENV.googleRedirectRegisterUrl,
      client_id: ENV.googleClientId,
      access_type: "offline",
      response_type: "code",
      prompt: "consent",
      scope: [
        "https://www.googleapis.com/auth/userinfo.profile",
        "https://www.googleapis.com/auth/userinfo.email",
        "https://www.googleapis.com/auth/youtube.readonly",
        "https://www.googleapis.com/auth/youtube",
        "https://www.googleapis.com/auth/youtube.upload",
        "https://www.googleapis.com/auth/gmail.modify",
        "https://www.googleapis.com/auth/gmail.compose",
        "https://www.googleapis.com/auth/drive",
        "https://www.googleapis.com/auth/calendar",
      ].join(" "),
    };
    const qs = new URLSearchParams(options);

    res.status(httpStatus.OK).send(`${rootUrl}?${qs.toString()}`);
  });

  done();
};

export const autoPrefix = "/oauth";
