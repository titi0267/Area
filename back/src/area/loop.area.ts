import { AreaService } from "../services";
import * as ServiceHelper from "../helpers/service.helpers";

const areaLoop = async () => {
  const areas = await AreaService.getEnabledAreas();

  for (const area of areas) {
    const action = ServiceHelper.getActionFct(
      area.actionServiceId,
      area.actionId,
    );

    if (action === null) continue;
    try {
      console.log("ar");
      const reactionParam = await action(area);

      if (reactionParam === null) continue;

      const reaction = ServiceHelper.getReactionFct(
        area.reactionServiceId,
        area.reactionId,
      );

      if (reaction === null) continue;

      console.log("ea");
      await reaction(reactionParam, area.userId);
    } catch (e) {
      console.log(e);
    }
  }
};

export default areaLoop;
