import { Area } from "@prisma/client";

export interface Service {
  id: number;
  serviceName: ServiceName;
  imageUrl: string;
  backgroundColor: string;
  oauthName: OauthService;
  actions: Action[];
  reactions: Reaction[];
}

interface Action {
  id: number;
  actionName: string;
  actionParamName: string;
  fct: (area: Area) => Promise<string | null>;
}

interface Reaction {
  id: number;
  reactionName: string;
  reactionParamName: string;
  fct: (reactionParam: string, userId: number) => void;
}

export type ServiceName =
  | "Youtube"
  | "Twitter"
  | "Discord"
  | "Spotify"
  | "Github";

export type OauthService = "google" | "spotify" | "github" | "none";
