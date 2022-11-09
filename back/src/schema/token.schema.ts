import Ajv, { JSONSchemaType } from "ajv";
import { RemoveTokenBody } from "../types/body/tokenRequestBody.types";

const ajv = new Ajv();

const removeTokenBodySchema: JSONSchemaType<RemoveTokenBody> = {
  type: "object",
  properties: {
    googleToken: { type: "boolean", nullable: true },
    githubToken: { type: "boolean", nullable: true },
    spotifyToken: { type: "boolean", nullable: true },
    discordGuildId: { type: "boolean", nullable: true },
    discordToken: { type: "boolean", nullable: true },
  },
  required: [],
  additionalProperties: false,
};

const removeTokenBodyValidator = ajv.compile(removeTokenBodySchema);

export { removeTokenBodyValidator };
