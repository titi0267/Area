import { User } from "@prisma/client";
import { PrismaClient } from "@prisma/client";
import httpStatus from "http-status";
import jwt from "jsonwebtoken";
import bcrypt from "bcrypt";

import ClientError from "../error";
import { Token } from "../types/global.types";
import ENV from "../env";

const prisma = new PrismaClient();

const getAllUsers = async (): Promise<User[]> => {
  return await prisma.user.findMany();
};

const createUser = async (
  firstName: string,
  lastName: string,
  email: string,
  password: string,
): Promise<Token> => {
  const userWithSameMail = await prisma.user.findFirst({ where: { email } });

  if (userWithSameMail !== null) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "email already taken",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const hashedPassword = await bcrypt.hash(password, 10);

  const user = await prisma.user.create({
    data: {
      email,
      firstName,
      lastName,
      password: hashedPassword,
      tokensTable: { create: {} },
    },
  });

  const token = jwt.sign({ id: user.id, email, role: user.role }, ENV.secret);

  return { token };
};

const loginUser = async (email: string, password: string): Promise<Token> => {
  const user = await prisma.user.findUnique({ where: { email } });

  if (user === null) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "email does not exist",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const same = await bcrypt.compare(password, user.password);

  if (same === false) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "password doesn't match",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const token = jwt.sign({ id: user.id, email, role: user.role }, ENV.secret);

  return { token };
};

const removeUserById = async (id: string | number): Promise<User> => {
  const formatedId = typeof id === "string" ? parseInt(id) : id;

  const doesUserExist = await prisma.user.findFirst({
    where: { id: formatedId },
  });

  if (doesUserExist === null) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "id out of range",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  await prisma.tokensTable.delete({
    where: { userId: formatedId },
  });
  const user = await prisma.user.delete({
    where: { id: formatedId },
  });

  return user;
};

const removeUserByEmail = async (email: string): Promise<User> => {
  const doesUserExist = await prisma.user.findFirst({
    where: { email },
  });

  if (doesUserExist === null) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "email does not exist range",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  await prisma.tokensTable.delete({ where: { userId: doesUserExist.id } });
  const user = await prisma.user.delete({
    where: { email },
  });

  return user;
};

export default {
  getAllUsers,
  createUser,
  removeUserById,
  removeUserByEmail,
  loginUser,
};
