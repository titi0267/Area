import Ajv, { JSONSchemaType } from "ajv";
import { AreaBody } from "../types/body/areaRequestBody.types";

const ajv = new Ajv();

const areaBodySchema: JSONSchemaType<AreaBody> = {
  type: "object",
  properties: {
    actionServiceId: { type: "integer" },
    actionId: { type: "integer" },
    actionParam: { type: "string" },
    reactionServiceId: { type: "integer" },
    reactionId: { type: "integer" },
    reactionParam: { type: "string" },
  },
  required: [
    "actionServiceId",
    "actionId",
    "actionParam",
    "reactionServiceId",
    "reactionId",
    "reactionParam",
  ],
  additionalProperties: false,
};

const areaBodyValidator = ajv.compile(areaBodySchema);

export { areaBodyValidator };
