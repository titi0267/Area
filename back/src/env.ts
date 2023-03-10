import * as dotenv from "dotenv";

dotenv.config();

const ENV = {
  port: (process.env.PORT as string) || "8080",
  secret: process.env.SECRET as string,
  clientPort: (process.env.CLIENT_PORT as string) || "8081",
  clientUrl: (process.env.CLIENT_URL as string) || "localhost",
  googleApiKey: process.env.YOUTUBE_API_TOKEN as string,
  googleClientId: process.env.GOOGLE_CLIENT_ID as string,
  googleClientSecret: process.env.GOOGLE_CLIENT_SECRET as string,
  googleRedirectUrl: process.env.GOOGLE_REDIRECT_URL as string,
  googleRedirectRegisterUrl: process.env.GOOGLE_REDIRECT_REGISTER_URL as string,
  spotifyClientId: process.env.SPOTIFY_CLIENT_ID as string,
  spotifyClientSecret: process.env.SPOTIFY_CLIENT_SECRET as string,
  spotifyRedirectUrl: process.env.SPOTIFY_REDIRECT_URL as string,
  githubClientId: process.env.GITHUB_CLIENT_ID as string,
  githubClientSecret: process.env.GITHUB_CLIENT_SECRET as string,
  githubRedirectUrl: process.env.GITHUB_REDIRECT_URL as string,
  host: (process.env.HOST as string) || "0.0.0.0",
  discordBotToken: process.env.DISCORD_BOT_TOKEN as string,
  discordClientId: process.env.DISCORD_CLIENT_ID as string,
  discordClientSecret: process.env.DISCORD_CLIENT_SECRET as string,
  discordRedirectUrl: process.env.DISCORD_REDIRECT_URL as string,
  openWeatherApiKey: process.env.OPEN_WEATHER_API_KEY as string,
};

export default ENV;
