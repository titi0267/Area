import { Area } from "@prisma/client";

export interface Service {
  id: number;
  serviceName: ServiceName;
  imageUrl: string;
  backgroundColor: string;
  oauthName: OauthService | null;
  actions: Action[];
  reactions: Reaction[];
}

export interface DiscordInfos {
  guildId: string;
  discordToken: string;
}

export interface IdParam {
  id: string;
}

interface Action {
  id: number;
  actionName: string;
  actionParamName: string;
  availableInjectParams: string[];
  paramFormat: RegExp | null;
  description: string;
  fct: (area: Area) => Promise<string | null>;
}

interface Reaction {
  id: number;
  reactionName: string;
  reactionParamName: string;
  paramFormat: RegExp | null;
  description: string;
  fct: (reactionParam: string, userId: number) => void;
}

export type ServiceName =
  | "Youtube"
  | "Twitter"
  | "Discord"
  | "Spotify"
  | "Github"
  | "Gmail"
  | "Weather"
  | "Calendar";

export type OauthService = "google" | "spotify" | "github" | "discord";
