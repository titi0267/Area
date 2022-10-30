import fastify from "fastify";
import fastifyAutoload from "@fastify/autoload";
import * as path from "path";
import { PrismaClient } from "@prisma/client";
import fastifyCors from "@fastify/cors";

import ENV from "./env";
import { UserInfos } from "./types/global.types";
import areaLoop from "./area/loop.area";
import { newMailFrom } from "./area/gmail/gmail.action";

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
    origin: `http://localhost:${ENV.clientPort}`,
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
  newMailFrom({
    id: 1,
    actionId: 1,
    actionServiceId: 1,
    actionParam: "jsutter@sac-indus.fr",
    reactionId: 1,
    reactionParam: "",
    reactionServiceId: 1,
    userId: 79,
    lastActionFetch: new Date(),
    lastActionValue: null,
  });
}, 0.1 * 60 * 1000);

main()
  .catch(e => {
    throw e;
  })
  .finally(async () => {
    await prisma.$disconnect();
  });
