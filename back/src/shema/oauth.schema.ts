import Ajv, { JSONSchemaType } from "ajv";
import {
  GoogleOauthQuery,
  SpotifyOauthQuery,
} from "../types/query/oauthRequestQuery.types";

const ajv = new Ajv();

const googleOauthQuerySchema: JSONSchemaType<GoogleOauthQuery> = {
  type: "object",
  properties: {
    code: { type: "string" },
  },
  required: ["code"],
};

const spotifyOauthQuerySchema: JSONSchemaType<SpotifyOauthQuery> = {
  type: "object",
  properties: {
    code: { type: "string" },
  },
  required: ["code"],
};

const googleOauthQueryValidator = ajv.compile(googleOauthQuerySchema);

const spotifyOauthQueryValidator = ajv.compile(spotifyOauthQuerySchema);

export { googleOauthQueryValidator, spotifyOauthQueryValidator };
