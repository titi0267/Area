import { PrismaClient, Area, User } from "@prisma/client";
import ClientError from "../error";
import httpStatus from "http-status";

import * as ServiceHelper from "../helpers/service.helpers";

const prisma = new PrismaClient();

const createArea = async (
  actionServiceId: number,
  actionId: number,
  actionParam: string,
  reactionServiceId: number,
  reactionId: number,
  reactionParam: string,
  userId: number,
): Promise<Area> => {
  ServiceHelper.rejectInvalidArea(
    actionServiceId,
    actionId,
    actionParam,
    reactionServiceId,
    reactionId,
    reactionParam,
  );

  const doesUserExist = await prisma.user.findUnique({
    where: { id: userId },
  });

  if (doesUserExist === null) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "email does not exist range",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }
  const area = prisma.area.create({
    data: {
      actionServiceId,
      actionId,
      actionParam,
      reactionServiceId,
      reactionId,
      reactionParam,
      userId,
    },
  });
  return area;
};

const getAllArea = async (): Promise<Area[]> => {
  return await prisma.area.findMany();
};

const getUserAreaById = async (
  userId: number,
  areaId: number,
): Promise<Area> => {
  const doesUserExist = await prisma.user.findUnique({ where: { id: userId } });
  const area = await prisma.area.findUnique({ where: { id: areaId } });

  if (!doesUserExist || !area) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "userId or areaId does not exist",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  if (area.userId !== userId) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "can't edit other peoples area",
      level: "warm",
      status: httpStatus.UNAUTHORIZED,
    });
  }

  return area;
};

const removeAreaById = async (id: string | number): Promise<Area> => {
  const formatedId = typeof id === "string" ? parseInt(id) : id;

  const doesAreaExist = await prisma.area.findFirst({
    where: { id: formatedId },
  });
  if (doesAreaExist === null) {
    throw new ClientError({
      name: "Invalid Area Id",
      message: "id out of range",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const area = await prisma.area.delete({
    where: { id: formatedId },
  });
  return area;
};

const getAreasByUserId = async (id: number): Promise<Area[]> => {
  const doesUserExist = await prisma.user.findUnique({ where: { id } });

  if (doesUserExist === null) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "Id does not exist",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const areas = await prisma.area.findMany({ where: { userId: id } });

  return areas;
};

const getEnabledAreas = async (): Promise<Area[]> => {
  const areas = await prisma.area.findMany({ where: { enabled: true } });

  return areas;
};

const updateAreaValues = async (
  id: number,
  updateValue: string | null,
): Promise<Area> => {
  const doesAreaExist = await prisma.area.findUnique({ where: { id } });

  if (doesAreaExist === null) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "Id does not exist",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const area = await prisma.area.update({
    where: { id },
    data: { lastActionValue: updateValue, lastActionFetch: new Date() },
  });

  return area;
};

const editArea = async (
  userId: number,
  areaId: number,
  enabled: boolean | null,
  actionParam: string | null,
  reactionParam: string | null,
): Promise<Area> => {
  const doesUserExist = await prisma.user.findUnique({ where: { id: userId } });
  const doesAreaExist = await prisma.area.findUnique({ where: { id: areaId } });

  if (!doesUserExist || !doesAreaExist) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "userId or areaId does not exist",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  if (doesAreaExist.userId !== userId) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "can't edit other peoples area",
      level: "warm",
      status: httpStatus.UNAUTHORIZED,
    });
  }

  const area = await prisma.area.update({
    where: { id: areaId },
    data: {
      enabled: enabled !== null ? enabled : undefined,
      actionParam: actionParam !== null ? actionParam : undefined,
      reactionParam: reactionParam !== null ? reactionParam : undefined,
    },
  });

  return area;
};

export default {
  createArea,
  getAllArea,
  getUserAreaById,
  removeAreaById,
  getAreasByUserId,
  getEnabledAreas,
  updateAreaValues,
  editArea,
};
