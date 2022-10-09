import { Client, GatewayIntentBits, Message } from "discord.js";
import ENV from "../../env";

const sendMessageToServer = async (reactionParam: string, userId: string) => {
  const client = new Client({
    intents: [GatewayIntentBits.Guilds],
  });

  client.login(ENV.discordBotToken);

  const firstGuild = (await client.guilds.fetch()).first();

  if (firstGuild === undefined) return;

  const guild = await firstGuild.fetch();

  try {
    const channels = await guild.channels.fetch();

    const channel = channels.find(item => item?.name === "général");

    if (channel?.isTextBased()) channel.send(reactionParam);
    else {
      console.log("The server" + guild.name + "has no channel");
    }
  } catch (e) {
    console.log("Cannot send message to guild " + guild.name);
  }
};

export { sendMessageToServer };
