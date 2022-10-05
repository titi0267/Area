import { PrismaClient, Area, User } from "@prisma/client";
import { Token } from "../types/global.types";
import ClientError from "../error";
import httpStatus from "http-status";

const prisma = new PrismaClient();

const createArea = async (
  actionService: string,
  action: string,
  actionParam: string,
  reactionService: string,
  reaction: string,
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
      actionService,
      action,
      actionParam,
      reactionService,
      reaction,
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

export default { createArea, getAllArea, removeAreaById };
