import { PrismaClient, Area, User } from "@prisma/client";
import ClientError from "../error";
import httpStatus from "http-status";

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

export default { createArea, getAllArea, removeAreaById, getAreasByUserId };
