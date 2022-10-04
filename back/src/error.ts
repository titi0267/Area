import httpStatus from "http-status";
import { ClientErrorType } from "./types/global.types";

export default class ClientError extends Error implements ClientErrorType {
  level: string;
  message: string;
  name: string;
  status: number;

  constructor(error: ClientErrorType) {
    super();

    this.name = error.name;
    this.message = error.message;
    this.level = error.level;
    this.status = error.status || httpStatus.INTERNAL_SERVER_ERROR;
  }
}
