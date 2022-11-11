import { Area } from "@prisma/client";
import { google } from "googleapis";

import * as ServiceHelper from "../../helpers/service.helpers";
import { AreaService } from "../../services";

const newMailFrom = async (area: Area): Promise<string | null> => {
  const oAuth2Client = await ServiceHelper.getGoogleOauthClient(area.userId);

  if (!oAuth2Client) return null;

  const gmail = google.gmail({ version: "v1", auth: oAuth2Client });

  const messages = (
    await gmail.users.messages.list({
      userId: "me",
      q: "from:" + area.actionParam,
    })
  ).data;

  if (!messages.resultSizeEstimate) return null;
  if (area.lastActionValue === null) {
    await AreaService.updateAreaValues(
      area.id,
      String(messages.resultSizeEstimate),
    );
    return null;
  }

  if (!messages.messages || !messages.messages[0].id) return null;

  const mail = (
    await gmail.users.messages.get({
      userId: "me",
      id: messages.messages[0].id,
    })
  ).data;

  if (mail.snippet === undefined || mail.snippet === null || !mail.payload)
    return null;

  const subject = mail.payload.headers?.find(
    header => header.name === "Subject",
  );

  if (subject?.value === undefined) return null;

  const params = {
    content: mail.snippet,
    subject: subject.value,
  };

  console.log(params);

  if (messages.resultSizeEstimate > parseInt(area.lastActionValue)) {
    await AreaService.updateAreaValues(
      area.id,
      String(messages.resultSizeEstimate),
    );
    return ServiceHelper.injectParamInReaction<typeof params>(
      area.reactionParam,
      params,
    );
  }

  await AreaService.updateAreaValues(
    area.id,
    String(messages.resultSizeEstimate),
  );

  return null;
};

export { newMailFrom };
