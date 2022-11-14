import { Client, GatewayIntentBits, Message } from "discord.js";
import ENV from "../../env";
import { TokenService } from "../../services";

const sendMessageToServer = async (reactionParam: string, userId: number) => {
  const client = new Client({
    intents: [GatewayIntentBits.Guilds],
  });

  const discordInfos = await TokenService.getDiscordInfos(userId);

  if (!discordInfos) return;

  client.login(ENV.discordBotToken);

  const guildId = (await client.guilds.fetch()).find(
    guild => guild.id === discordInfos.guildId,
  );

  if (!guildId) return;

  const guild = await guildId.fetch();

  try {
    const channels = await guild.channels.fetch();

    const channel = channels.find(
      guildChannel => guildChannel?.isTextBased() === true,
    );

    if (channel?.isTextBased()) channel.send(reactionParam);
    else {
      console.log("The server" + guild.name + "has no channel");
    }
  } catch (e) {
    console.log("Cannot send message to guild " + guild.name);
  }
};

export { sendMessageToServer };
