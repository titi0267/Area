import { Area } from "@prisma/client";
import { google } from "googleapis";

import * as ServiceHelper from "../../helpers/service.helpers";
import { AreaService } from "../../services";

const newFileInDrive = async (area: Area): Promise<string | null> => {
  const oAuth2Client = await ServiceHelper.getGoogleOauthClient(area.userId);

  if (!oAuth2Client) return null;

  const drive = google.drive({ version: "v3", auth: oAuth2Client });

  const data = (await drive.files.list({ pageSize: 1000 })).data;

  if (!data.files || !data.files[0]) return null;

  if (area.lastActionValue === null) {
    await AreaService.updateAreaValues(area.id, String(data.files.length));
    return null;
  }

  if (!data.files[0].name || !data.files[0].mimeType) return null;

  if (data.files.length > parseInt(area.lastActionValue)) {
    await AreaService.updateAreaValues(area.id, String(data.files.length));
    return area.reactionParam;
  }

  await AreaService.updateAreaValues(area.id, String(data.files.length));

  return null;
};

export { newFileInDrive };
