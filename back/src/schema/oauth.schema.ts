import Ajv, { JSONSchemaType } from "ajv";
import {
  GoogleOauthBody,
  SpotifyOauthBody,
  GithubOauthBody,
  DiscordOauthBody,
} from "../types/body/oauthRequestBody.types";

const ajv = new Ajv();

const googleOauthQuerySchema: JSONSchemaType<GoogleOauthBody> = {
  type: "object",
  properties: {
    code: { type: "string" },
  },
  required: ["code"],
};

const spotifyOauthQuerySchema: JSONSchemaType<SpotifyOauthBody> = {
  type: "object",
  properties: {
    code: { type: "string" },
  },
  required: ["code"],
};

const githubOauthQuerySchema: JSONSchemaType<GithubOauthBody> = {
  type: "object",
  properties: {
    code: { type: "string" },
  },
  required: ["code"],
};

const discordOauthQuerySchema: JSONSchemaType<DiscordOauthBody> = {
  type: "object",
  properties: {
    code: { type: "string" },
    guild_id: { type: "string" },
    permissions: { type: "number" },
  },
  required: ["code", "guild_id", "permissions"],
};

const googleOauthQueryValidator = ajv.compile(googleOauthQuerySchema);

const spotifyOauthQueryValidator = ajv.compile(spotifyOauthQuerySchema);

const githubOauthQueryValidator = ajv.compile(githubOauthQuerySchema);

const discordOauthQueryValidator = ajv.compile(discordOauthQuerySchema);

export {
  googleOauthQueryValidator,
  spotifyOauthQueryValidator,
  githubOauthQueryValidator,
  discordOauthQueryValidator,
};
