import { TokensTable, User } from "@prisma/client";

export type FastifyPluginDoneFunction = (err?: Error) => void;

export interface Token {
  token: string;
}

export type ErrorLevel = "error" | "warm";

export interface ClientErrorType {
  level: string;
  message: string;
  name: string;
  status: number;
}

export interface UserInfos {
  id: number;
  email: string;
  role: Role;
}

enum Role {
  USER,
  ADMIN,
}

export interface DecodedToken {
  id: number;
  email: string;
  role: Role;
  expTime: Date;
}

export interface UserWithTokens extends User {
  tokensTable: TokensTable | null;
}
