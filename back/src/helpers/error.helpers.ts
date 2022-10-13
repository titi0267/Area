import httpStatus from "http-status";
import ClientError from "../error";

const throwBodyError = () => {
  throw new ClientError({
    name: "Missing elements",
    message: "Mandatory fields are missing",
    level: "warm",
    status: httpStatus.BAD_REQUEST,
  });
};

export { throwBodyError };
