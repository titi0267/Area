import * as dotenv from "dotenv";

dotenv.config();

const ENV = {
  port: (process.env.PORT as string) || "3000",
  secret: process.env.SECRET as string,
};

export default ENV;
