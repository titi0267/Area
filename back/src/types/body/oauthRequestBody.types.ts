export interface BaseOauthBody {
  code: string;
}

export interface DiscordOauthBody {
  code: string;
  guild_id: string;
}
