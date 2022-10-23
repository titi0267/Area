import Ajv, { JSONSchemaType } from "ajv";
import { TokenBody } from "../types/body/tokenRequestBody.types";

const ajv = new Ajv();

const tokenBodySchema: JSONSchemaType<TokenBody> = {
  type: "object",
  properties: {
    discordToken: { type: "string", nullable: true },
    twitterToken: { type: "string", nullable: true },
    githubToken: { type: "string", nullable: true },
    youtubeToken: { type: "string", nullable: true },
    spotifyToken: { type: "string", nullable: true },
    trelloToken: { type: "string", nullable: true },
    userId: { type: "integer" },
  },
  required: ["userId"],
  additionalProperties: false,
};

const tokenBodyValidator = ajv.compile(tokenBodySchema);

export { tokenBodyValidator };
