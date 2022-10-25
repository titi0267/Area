import Ajv, { JSONSchemaType } from "ajv";
import {
  GoogleOauthBody,
  SpotifyOauthBody,
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

const googleOauthQueryValidator = ajv.compile(googleOauthQuerySchema);

const spotifyOauthQueryValidator = ajv.compile(spotifyOauthQuerySchema);

export { googleOauthQueryValidator, spotifyOauthQueryValidator };
