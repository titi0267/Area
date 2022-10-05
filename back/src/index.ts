import fastify from "fastify";
import fastifyAutoload from "@fastify/autoload";
import * as path from "path";
import { PrismaClient } from "@prisma/client";
import fastifyCors from "@fastify/cors";

import ENV from "./env";

const prisma = new PrismaClient();
const server = fastify();

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

main()
  .catch(e => {
    throw e;
  })
  .finally(async () => {
    await prisma.$disconnect();
  });
