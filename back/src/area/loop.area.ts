import { SERVICES } from "../constants/serviceList";
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
    const doReaction = await action(area.actionParam, "", area.lastActionFetch);

    if (doReaction === false) return;

    const reaction = ServiceHelper.getReactionFct(
      area.reactionServiceId,
      area.reactionId,
    );

    if (reaction === null) return;

    reaction(area.reactionParam, "");
  }
};

export default areaLoop;
