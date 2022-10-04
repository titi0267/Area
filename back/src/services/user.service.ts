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

export default { getAllUsers, createUser };
