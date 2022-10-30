export interface GoogleOauthBody {
  code: string;
}

export interface SpotifyOauthBody {
  code: string;
}

export interface GithubOauthBody {
  code: string;
}

export interface DiscordOauthBody {
  code: string;
  permissions: number;
  guild_id: string;
}
