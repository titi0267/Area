import fastify from "fastify";
import fastifyAutoload from "@fastify/autoload";
import * as path from "path";
import { PrismaClient } from "@prisma/client";
import fastifyCors from "@fastify/cors";

import ENV from "./env";
import { UserInfos } from "./types/global.types";
import { AreaService } from "./services";
import { SERVICES } from "./constants/serviceList";
import areaLoop from "./area/loop.area";
import { postNewTweet } from "./ServiceRequest/twitter/twitter.reaction";
import { sendMessageToServer } from "./ServiceRequest/discord/discord.reaction";
import { checkVideoLike } from "./ServiceRequest/youtube/youtube.action";

const prisma = new PrismaClient();
const server = fastify();

declare module "fastify" {
  interface FastifyRequest {
    user: UserInfos | undefined;
  }
}

const main = async () => {
  server.register(fastifyAutoload, {
    dir: path.join(__dirname, "routes"),
  });

  server.register(fastifyCors, {
    credentials: true,
    origin: "http://localhost:8080",
  });

  server.listen(
    { port: parseInt(ENV.port), host: ENV.host },
    (err, address) => {
      if (err) {
        console.error(err);
        process.exit(1);
      }
      console.log(`Server listening at ${address}`);
    },
  );
};

setInterval(async () => {
  await areaLoop();
}, 1 * 60 * 1000);

main()
  .catch(e => {
    throw e;
  })
  .finally(async () => {
    await prisma.$disconnect();
  });
