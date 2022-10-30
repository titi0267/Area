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

  if (messages.resultSizeEstimate > parseInt(area.lastActionValue)) {
    await AreaService.updateAreaValues(
      area.id,
      String(messages.resultSizeEstimate),
    );
    return area.reactionParam;
  }

  await AreaService.updateAreaValues(
    area.id,
    String(messages.resultSizeEstimate),
  );

  return null;
};

export { newMailFrom };
