import { User } from "@prisma/client";
import { PrismaClient } from "@prisma/client";
import httpStatus from "http-status";
import jwt from "jsonwebtoken";
import bcrypt from "bcrypt";

import ClientError from "../error";
import { Token, UserWithTokens } from "../types/global.types";
import ENV from "../env";
import { TokenService } from ".";
import { google } from "googleapis";

const prisma = new PrismaClient();

const getAllUsers = async (): Promise<Omit<User, "password">[]> => {
  return await prisma.user.findMany({
    select: {
      email: true,
      id: true,
      password: false,
      firstName: true,
      lastName: true,
      role: true,
    },
  });
};

const getOneUser = async (userId: number): Promise<UserWithTokens> => {
  const user = await prisma.user.findUnique({
    where: { id: userId },
    select: {
      email: true,
      id: true,
      password: false,
      firstName: true,
      lastName: true,
      role: true,
      tokensTable: true,
    },
  });

  if (!user) {
    throw new ClientError({
      name: "Invalid User Id",
      message: "user id does not exist",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  return user;
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

  const exp = new Date(new Date().getTime() + 2 * 24 * 60 * 60 * 1000);

  const token = jwt.sign(
    { id: user.id, email, role: user.role, expTime: exp },
    ENV.secret,
  );

  return { token };
};

const connectOauthUser = async (
  firstName: string | null,
  lastName: string | null,
  email: string | null,
  id: string | null,
  googleToken: string | null,
): Promise<Token> => {
  if (!firstName || !email || !id || !googleToken) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "Invalid google account",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const userWithSameMail = await prisma.user.findFirst({ where: { email } });

  if (userWithSameMail !== null) return await loginUser(email, id);

  const hashedPassword = await bcrypt.hash(id, 10);

  const user = await prisma.user.create({
    data: {
      email,
      firstName,
      lastName: lastName || "",
      password: hashedPassword,
      tokensTable: { create: {} },
    },
  });

  const exp = new Date(new Date().getTime() + 2 * 24 * 60 * 60 * 1000);

  const token = jwt.sign(
    { id: user.id, email, role: user.role, expTime: exp },
    ENV.secret,
  );

  await TokenService.setGoogleToken(user.id, googleToken);

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
  connectOauthUser,
  createUser,
  removeUserById,
  removeUserByEmail,
  loginUser,
  getOneUser,
};
