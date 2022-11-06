import { PrismaClient, TokensTable } from "@prisma/client";
import httpStatus from "http-status";
import ClientError from "../error";
import { DiscordInfos } from "../types/areaServices/areaServices.types";

const prisma = new PrismaClient();

const setGithubToken = async (
  userId: number,
  token: string | undefined | null,
): Promise<TokensTable> => {
  if (!token) {
    throw new ClientError({
      name: "Invalid Token",
      message: "Given token is invalid",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }
  const tokensTableExist = await prisma.tokensTable.findUnique({
    where: { userId },
  });

  if (!tokensTableExist) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "UserId does not exist",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const tokenTable = await prisma.tokensTable.update({
    where: {
      userId,
    },
    data: {
      githubToken: token,
    },
  });

  return tokenTable;
};

const setGoogleToken = async (
  userId: number,
  token: string | undefined | null,
): Promise<TokensTable> => {
  if (!token) {
    throw new ClientError({
      name: "Invalid Token",
      message: "Given token is invalid",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const tokensTableExist = await prisma.tokensTable.findUnique({
    where: { userId },
  });

  if (!tokensTableExist) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "UserId does not exist",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const tokenTable = await prisma.tokensTable.update({
    where: {
      userId,
    },
    data: {
      googleToken: token,
    },
  });

  return tokenTable;
};

const setSpotifyToken = async (
  userId: number,
  token: string | undefined | null,
): Promise<TokensTable> => {
  if (!token) {
    throw new ClientError({
      name: "Invalid Token",
      message: "Given token is invalid",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const tokensTableExist = await prisma.tokensTable.findUnique({
    where: { userId },
  });

  if (!tokensTableExist) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "UserId does not exist",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const tokenTable = await prisma.tokensTable.update({
    where: {
      userId,
    },
    data: {
      spotifyToken: token,
    },
  });

  return tokenTable;
};

const setDiscordInfos = async (
  userId: number,
  token: string,
  guildId: string,
): Promise<TokensTable> => {
  const tokensTableExist = await prisma.tokensTable.findUnique({
    where: { userId },
  });

  if (!tokensTableExist) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "UserId does not exist",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const tokenTable = await prisma.tokensTable.update({
    where: {
      userId,
    },
    data: {
      discordToken: token,
      discordGuildId: guildId,
    },
  });

  return tokenTable;
};

const getDiscordInfos = async (
  userId: number,
): Promise<DiscordInfos | null> => {
  const tokensTable = await prisma.tokensTable.findUnique({
    where: { userId },
  });

  if (!tokensTable?.discordGuildId || !tokensTable.discordToken) return null;

  return {
    guildId: tokensTable.discordGuildId,
    discordToken: tokensTable.discordToken,
  };
};

const getGithubToken = async (userId: number): Promise<string | null> => {
  const tokensTable = await prisma.tokensTable.findUnique({
    where: { userId },
  });

  return tokensTable?.githubToken || null;
};

const getGoogleToken = async (userId: number): Promise<string | null> => {
  const tokensTable = await prisma.tokensTable.findUnique({
    where: { userId },
  });

  return tokensTable?.googleToken || null;
};

const getSpotifyToken = async (userId: number): Promise<string | null> => {
  const tokensTable = await prisma.tokensTable.findUnique({
    where: { userId },
  });

  return tokensTable?.spotifyToken || null;
};

const removeUserToken = async (
  userId: number,
  discordGuild: boolean | undefined,
  discordToken: boolean | undefined,
  googleToken: boolean | undefined,
  spotifyToken: boolean | undefined,
  githubToken: boolean | undefined,
): Promise<TokensTable> => {
  const tokensTableExist = await prisma.tokensTable.findUnique({
    where: { userId },
  });

  if (!tokensTableExist) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "UserId does not exist",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const tokenTable = await prisma.tokensTable.update({
    where: { userId },
    data: {
      discordGuildId:
        discordGuild === true ? null : tokensTableExist.discordGuildId,
      discordToken:
        discordToken === true ? null : tokensTableExist.discordToken,
      githubToken: githubToken === true ? null : tokensTableExist.githubToken,
      googleToken: googleToken === true ? null : tokensTableExist.googleToken,
      spotifyToken:
        spotifyToken === true ? null : tokensTableExist.spotifyToken,
    },
  });

  return tokenTable;
};

export default {
  setGithubToken,
  setGoogleToken,
  setSpotifyToken,
  setDiscordInfos,
  getGithubToken,
  getGoogleToken,
  getSpotifyToken,
  getDiscordInfos,
  removeUserToken,
};
