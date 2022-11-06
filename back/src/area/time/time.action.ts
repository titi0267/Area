import { Area } from "@prisma/client";
import * as ServiceHelper from "../../helpers/service.helpers";
import { AreaService } from "../../services";

const everyDayAt = async (area: Area): Promise<string | null> => {
  const time = ServiceHelper.getTime(area.actionParam);

  if (!time) return null;

  const lastFetch = {
    hours: area.lastActionFetch.getHours(),
    minutes: area.lastActionFetch.getMinutes(),
    day: area.lastActionFetch.getDay(),
  };

  const alreadyDoneToday =
    lastFetch.day === new Date().getDay() &&
    lastFetch.hours === time.hours &&
    lastFetch.minutes === time.minutes;

  if (
    time.hours === new Date().getHours() &&
    time.minutes === new Date().getMinutes() &&
    !alreadyDoneToday
  ) {
    await AreaService.updateAreaValues(area.id, "");
    return area.reactionParam;
  }

  return null;
};

export { everyDayAt };
