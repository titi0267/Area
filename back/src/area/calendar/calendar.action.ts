import { Area } from "@prisma/client";
import { google } from "googleapis";

import * as ServiceHelper from "../../helpers/service.helpers";
import { AreaService } from "../../services";

const newCalendarEvent = async (area: Area): Promise<string | null> => {
  const oAuth2Client = await ServiceHelper.getGoogleOauthClient(area.userId);

  if (!oAuth2Client) return null;

  const calendar = google.calendar({ version: "v3", auth: oAuth2Client });

  const eventList = (
    await calendar.events.list({
      calendarId: "primary",
      timeMin: new Date().toISOString(),
    })
  ).data;

  if (!eventList.items) return null;

  if (area.lastActionValue === null || eventList.items.length === 0) {
    await AreaService.updateAreaValues(area.id, String(eventList.items.length));
    return null;
  }

  const lastEvent = eventList.items[eventList.items.length - 1];

  if (!lastEvent || !lastEvent.summary || !lastEvent.creator?.email)
    return null;

  const params = {
    summary: lastEvent.summary,
    creator: lastEvent.creator.email,
  };

  if (parseInt(area.lastActionValue) < eventList.items.length) {
    await AreaService.updateAreaValues(area.id, String(eventList.items.length));
    return ServiceHelper.injectParamInReaction<typeof params>(
      area.reactionParam,
      params,
    );
  }

  await AreaService.updateAreaValues(area.id, String(eventList.items.length));

  return null;
};

export { newCalendarEvent };
