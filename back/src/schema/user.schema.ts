import Ajv, { JSONSchemaType } from "ajv";
import { RegisterBody, LoginBody } from "../types/body/userRequestBody.types";

const ajv = new Ajv();

const registerBodySchema: JSONSchemaType<RegisterBody> = {
  type: "object",
  properties: {
    firstName: { type: "string" },
    lastName: { type: "string" },
    email: { type: "string" },
    password: { type: "string" },
  },
  required: ["firstName", "lastName", "email", "password"],
  additionalProperties: false,
};

const loginBodySchema: JSONSchemaType<LoginBody> = {
  type: "object",
  properties: {
    email: { type: "string" },
    password: { type: "string" },
  },
  required: ["email", "password"],
  additionalProperties: false,
};

const registerBodyValidator = ajv.compile(registerBodySchema);

const loginBodyValidator = ajv.compile(loginBodySchema);

export { registerBodyValidator, loginBodyValidator };
