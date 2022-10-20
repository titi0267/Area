import { PrismaClient, TokensTable } from "@prisma/client";

const prisma = new PrismaClient();

const setGithubToken = async (
  userId: number,
  token: string,
): Promise<TokensTable | null> => {
  const tokensTableExist = await prisma.tokensTable.findUnique({
    where: { userId },
  });

  if (!tokensTableExist) return null;

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
  token: string,
): Promise<TokensTable | null> => {
  const tokensTableExist = await prisma.tokensTable.findUnique({
    where: { userId },
  });

  if (!tokensTableExist) return null;

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
  token: string,
): Promise<TokensTable | null> => {
  const tokensTableExist = await prisma.tokensTable.findUnique({
    where: { userId },
  });

  if (!tokensTableExist) return null;

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

export default {
  setGithubToken,
  setGoogleToken,
  setSpotifyToken,
  getGithubToken,
  getGoogleToken,
  getSpotifyToken,
};
