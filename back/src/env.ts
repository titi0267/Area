import * as dotenv from "dotenv";

dotenv.config();

const ENV = {
  port: (process.env.PORT as string) || "3000",
  secret: process.env.SECRET as string,
  host: (process.env.HOST as string) || "0.0.0.0",
};

export default ENV;
