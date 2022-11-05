import { google } from "googleapis";
import MailComposer from "nodemailer/lib/mail-composer";

import * as ServiceHelper from "../../helpers/service.helpers";

const sendEmail = async (
  reactionParam: string,
  userId: number,
): Promise<void> => {
  const oAuth2Client = await ServiceHelper.getGoogleOauthClient(userId);
  const mailContent = ServiceHelper.getMailContentParams(reactionParam);

  if (!oAuth2Client || !mailContent) return;

  const gmail = google.gmail({ version: "v1", auth: oAuth2Client });
  const mailComposer = new MailComposer({
    to: mailContent.to,
    subject: mailContent.subject,
    text: mailContent.content,
  });

  const message = await mailComposer.compile().build();
  const rawMessage = Buffer.from(message)
    .toString("base64")
    .replace(/\+/g, "-")
    .replace(/\//g, "_")
    .replace(/=+$/, "");

  const sendedMessage = await gmail.users.messages.send({
    userId: "me",
    requestBody: {
      raw: rawMessage,
    },
  });

  console.log(sendedMessage);
};

export { sendEmail };
