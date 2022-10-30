import { Area } from "@prisma/client";

import * as ServiceHelper from "../../helpers/service.helpers";
import { AreaService } from "../../services";

const checkNewFollowingUser = async (area: Area): Promise<string | null> => {
  const githubClient = await ServiceHelper.getGithubClient(area.userId);

  if (!githubClient) return null;

  const followingUser = (
    await githubClient.rest.users.listFollowersForAuthenticatedUser()
  ).data;

  if (area.lastActionValue == null) {
    await AreaService.updateAreaValues(area.id, String(followingUser.length));
    return null;
  }

  const lastFollowingUser = followingUser[followingUser.length - 1];

  if (!lastFollowingUser.name) return null;

  const params = {
    lastFollowingUserName: lastFollowingUser.name,
  };

  if (parseInt(area.lastActionValue) < followingUser.length) {
    await AreaService.updateAreaValues(area.id, String(followingUser.length));
    return ServiceHelper.injectParamInReaction<typeof params>(
      area.reactionParam,
      params,
    );
  }
  return null;
};

export { checkNewFollowingUser };
