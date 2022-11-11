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

  if (!lastFollowingUser.login) return null;

  const params = {
    lastFollowingUserName: lastFollowingUser.login,
  };
  console.log(followingUser);

  if (parseInt(area.lastActionValue) < followingUser.length) {
    await AreaService.updateAreaValues(area.id, String(followingUser.length));
    return ServiceHelper.injectParamInReaction<typeof params>(
      area.reactionParam,
      params,
    );
  }

  await AreaService.updateAreaValues(area.id, String(followingUser.length));
  return null;
};

const newIssue = async (area: Area): Promise<string | null> => {
  const githubClient = await ServiceHelper.getGithubClient(area.userId);

  if (!githubClient) return null;

  const issues = (
    await githubClient.rest.issues.list({ filter: "all", state: "open" })
  ).data;

  if (!issues[0] || !issues[0].repository) return null;

  if (area.lastActionValue === null) {
    await AreaService.updateAreaValues(area.id, "");
    return null;
  }

  const params = {
    title: issues[0].title,
    repositoryName: issues[0].repository.name,
  };

  if (new Date(issues[0].created_at) > area.lastActionFetch) {
    await AreaService.updateAreaValues(area.id, issues[0].created_at);
    return ServiceHelper.injectParamInReaction<typeof params>(
      area.reactionParam,
      params,
    );
  }

  await AreaService.updateAreaValues(area.id, "");

  return null;
};

const newPullRequestOnRepository = async (
  area: Area,
): Promise<string | null> => {
  const githubClient = await ServiceHelper.getGithubClient(area.userId);
  const githubPullRequestParams = ServiceHelper.getGithubPullRequestParams(
    area.actionParam,
  );

  if (!githubClient || !githubPullRequestParams) return null;

  const pulls = (
    await githubClient.rest.pulls.list({
      owner: githubPullRequestParams.owner,
      repo: githubPullRequestParams.repo,
    })
  ).data;

  if (area.lastActionValue === null) {
    await AreaService.updateAreaValues(area.id, "");
    return null;
  }

  if (!pulls[0] || !pulls[0].user?.login) return null;

  const params = {
    title: pulls[0].title,
    creator: pulls[0].user?.login,
    body: pulls[0].body || "",
  };

  if (new Date(pulls[0].created_at) > area.lastActionFetch) {
    await AreaService.updateAreaValues(area.id, pulls[0].created_at);
    return ServiceHelper.injectParamInReaction<typeof params>(
      area.reactionParam,
      params,
    );
  }

  await AreaService.updateAreaValues(area.id, "");

  return null;
};

export { checkNewFollowingUser, newIssue, newPullRequestOnRepository };
