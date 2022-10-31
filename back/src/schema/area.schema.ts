import Ajv, { JSONSchemaType } from "ajv";
import { AreaBody, EditAreaBody } from "../types/body/areaRequestBody.types";

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

const editAreaBodySchema: JSONSchemaType<EditAreaBody> = {
  type: "object",
  properties: {
    areaId: { type: "number" },
    enabled: { type: "boolean" },
    actionParam: { type: "string" },
    reactionParam: { type: "string" },
  },
  required: ["areaId"],
  additionalProperties: false,
};

const areaBodyValidator = ajv.compile(areaBodySchema);

const editAreaBodyValidator = ajv.compile(editAreaBodySchema);

export { areaBodyValidator, editAreaBodyValidator };
