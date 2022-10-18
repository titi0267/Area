import Ajv, { JSONSchemaType } from "ajv";
import { GoogleOauthQuery } from "../types/query/oauthRequestQuery.types";

const ajv = new Ajv();

const googleOauthQuerySchema: JSONSchemaType<GoogleOauthQuery> = {
  type: "object",
  properties: {
    code: { type: "string" },
  },
  required: ["code"],
};

const googleOauthQueryValidator = ajv.compile(googleOauthQuerySchema);

export { googleOauthQueryValidator };
