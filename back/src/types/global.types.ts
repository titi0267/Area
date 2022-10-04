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
