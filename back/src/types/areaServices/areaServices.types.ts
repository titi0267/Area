import { Area } from "@prisma/client";

export interface Service {
  id: number;
  serviceName: ServiceName;
  actions: Action[];
  reactions: Reaction[];
}

interface Action {
  id: number;
  actionName: string;
  actionParamName: string;
  fct: (area: Area) => Promise<Boolean>;
}

interface Reaction {
  id: number;
  reactionName: string;
  reactionParamName: string;
  fct: (reactionParam: string, userId: string) => void;
}

export type ServiceName = "Youtube" | "Twitter" | "Discord";
