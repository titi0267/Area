import Ajv, { JSONSchemaType } from "ajv";
import {
  BaseOauthBody,
  DiscordOauthBody,
} from "../types/body/oauthRequestBody.types";

const ajv = new Ajv();

const googleOauthQuerySchema: JSONSchemaType<BaseOauthBody> = {
  type: "object",
  properties: {
    code: { type: "string" },
  },
  required: ["code"],
};

const spotifyOauthQuerySchema: JSONSchemaType<BaseOauthBody> = {
  type: "object",
  properties: {
    code: { type: "string" },
  },
  required: ["code"],
};

const githubOauthQuerySchema: JSONSchemaType<BaseOauthBody> = {
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
  },
  required: ["code", "guild_id"],
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
