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

const verifyIdParamIsNumber = (id: string): number => {
  const convertedId = parseInt(id);

  if (Number.isNaN(convertedId)) {
    throw new ClientError({
      name: "Invalid Param",
      message: "Request param must be a number",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  return convertedId;
};

export { throwBodyError, verifyIdParamIsNumber };
