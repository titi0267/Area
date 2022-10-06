import { PrismaClient, Area, TokensTable } from "@prisma/client";
import httpStatus from "http-status";
import ClientError from "../error";

const prisma = new PrismaClient();

const updateToken = async (
  discordToken: string | undefined,
  twitterToken: string | undefined,
  githubToken: string | undefined,
  youtubeToken: string | undefined,
  trelloToken: string | undefined,
  spotifyToken: string | undefined,
  userId: number,
): Promise<TokensTable> => {
  const doesUserExist = await prisma.user.findFirst({
    where: { id: userId },
  });
  if (doesUserExist === null) {
    throw new ClientError({
      name: "Invalid Credential",
      message: "id out of range",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const token = await prisma.tokensTable.update({
    where: {
      id: userId,
    },
    data: {
      discordToken,
      twitterToken,
      githubToken,
      youtubeToken,
      trelloToken,
      spotifyToken,
    },
  });

  return token;
};

export default { updateToken };
