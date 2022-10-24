import { AreaService } from "../services";
import * as ServiceHelper from "../helpers/service.helpers";

const areaLoop = async () => {
  const areas = await AreaService.getAllArea();

  for (const area of areas) {
    const action = ServiceHelper.getActionFct(
      area.actionServiceId,
      area.actionId,
    );

    if (action === null) return;
    const reactionParam = await action(area);

    if (reactionParam === null) return;

    const reaction = ServiceHelper.getReactionFct(
      area.reactionServiceId,
      area.reactionId,
    );

    if (reaction === null) return;

    reaction(reactionParam, "");
  }
};

export default areaLoop;
