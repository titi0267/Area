import * as dotenv from "dotenv";

dotenv.config();

const ENV = {
  port: (process.env.PORT as string) || "8080",
  secret: process.env.SECRET as string,
  clientPort: (process.env.CLIENT_PORT as string) || "8081",
  googleApiKey: process.env.YOUTUBE_API_TOKEN as string,
  googleClientId: process.env.GOOGLE_CLIENT_ID as string,
  googleClientSecret: process.env.GOOGLE_CLIENT_SECRET as string,
  googleRedirectUrl: process.env.GOOGLE_REDIRECT_URL as string,
  googleRedirectUrlMobile: process.env.GOOGLE_REDIRECT_URL_MOBILE as string,
  spotifyClientId: process.env.SPOTIFY_CLIENT_ID as string,
  spotifyClientSecret: process.env.SPOTIFY_CLIENT_SECRET as string,
  spotifyRedirectUrl: process.env.SPOTIFY_REDIRECT_URL as string,
  spotifyRedirectUrlMobile: process.env.SPOTIFY_REDIRECT_URL_MOBILE as string,
  githubClientId: process.env.GITHUB_CLIENT_ID as string,
  githubClientSecret: process.env.GITHUB_CLIENT_SECRET as string,
  githubRedirectUrl: process.env.GITHUB_REDIRECT_URL as string,
  githubRedirectUrlMobile: process.env.GITHUB_REDIRECT_URL_MOBILE as string,
  host: (process.env.HOST as string) || "0.0.0.0",
  discordBotToken: process.env.DISCORD_BOT_TOKEN as string,
  discordClientId: process.env.DISCORD_CLIENT_ID as string,
  discordClientSecret: process.env.DISCORD_CLIENT_SECRET as string,
  discordRedirectUrl: process.env.DISCORD_REDIRECT_URL as string,
};

export default ENV;
