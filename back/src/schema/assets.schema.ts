import Ajv, { JSONSchemaType } from "ajv";
import { ImageParam } from "../types/global.types";

const ajv = new Ajv();

const getImageParamSchema: JSONSchemaType<ImageParam> = {
  type: "object",
  properties: {
    image: { type: "string" },
  },
  required: ["image"],
};

const getImageParamValidator = ajv.compile(getImageParamSchema);

export { getImageParamValidator };
