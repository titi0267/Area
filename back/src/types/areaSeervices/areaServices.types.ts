export interface Service {
  id: number;
  serviceName: ServiceName;
  action: Action[];
  reaction: Reaction[];
}

interface Action {
  id: number;
  actionName: string;
  actionParamName: string;
  fct: (
    actionParam: string,
    userToken: string,
    lastUpdate: Date,
  ) => Promise<Boolean>;
}

interface Reaction {
  id: number;
  reactionName: string;
  reactionParamName: string;
  fct: (reactionParam: string, userToken: string) => void;
}

export type ServiceName = "Youtube" | "Twitter";
