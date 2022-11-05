import { google } from "googleapis";

import * as ServiceHelper from "../../helpers/service.helpers";

const sendEmail = async (
  reactionParam: string,
  userId: number,
): Promise<void> => {
  const oAuth2Client = await ServiceHelper.getGoogleOauthClient(userId);
  const mailContent = ServiceHelper.getMailContentParams(reactionParam);

  if (!oAuth2Client || !mailContent) return;

  const gmail = google.gmail({ version: "v1", auth: oAuth2Client });

  const utf8Subject = `=?utf-8?B?${Buffer.from(mailContent.subject).toString(
    "base64",
  )}?=`;
  const messageParts = [
    "From: Justin Beckwith <beckwith@google.com>",
    `To: Justin Beckwith <${mailContent.to}>`,
    "Content-Type: text/html; charset=utf-8",
    "MIME-Version: 1.0",
    `Subject: ${utf8Subject}`,
    "",
    mailContent.content,
    "",
  ];
  const message = messageParts.join("\n");

  const encodedMessage = Buffer.from(message)
    .toString("base64")
    .replace(/\+/g, "-")
    .replace(/\//g, "_")
    .replace(/=+$/, "");

  await gmail.users.messages.send({
    userId: "me",
    requestBody: {
      raw: encodedMessage,
    },
  });
};

export { sendEmail };
