import { PrismaClient, Area, TokensTable } from "@prisma/client";
import { disconnect } from "process";

import { Token } from "../types/global.types";

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
  const token = await prisma.tokensTable.update({
    where: {
      userId,
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
