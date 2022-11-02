import Ajv, { JSONSchemaType } from "ajv";
import { AreaBody, EditAreaBody } from "../types/body/areaRequestBody.types";
import { IdParam } from "../types/areaServices/areaServices.types";

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

const deleteAreaParamSchema: JSONSchemaType<IdParam> = {
  type: "object",
  properties: {
    id: { type: "number" },
  },
  required: ["id"],
};

const getUserAreaByIdParamSchema: JSONSchemaType<IdParam> = {
  type: "object",
  properties: {
    id: { type: "number" },
  },
  required: ["id"],
  additionalProperties: false,
};

const areaBodyValidator = ajv.compile(areaBodySchema);

const editAreaBodyValidator = ajv.compile(editAreaBodySchema);

const deleteAreaParamValidator = ajv.compile(deleteAreaParamSchema);

const getUserAreaByIdParamValidator = ajv.compile(getUserAreaByIdParamSchema);

export {
  areaBodyValidator,
  editAreaBodyValidator,
  getUserAreaByIdParamValidator,
  deleteAreaParamValidator,
};
