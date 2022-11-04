import * as ServiceHelper from "../../helpers/service.helpers";

const createGithubIssue = async (reactionParam: string, userId: number) => {
  const githubClient = await ServiceHelper.getGithubClient(userId);

  if (!githubClient) return;

  const issueParam = ServiceHelper.getGithubIssueParams(reactionParam);

  if (!issueParam) return;

  await githubClient.rest.issues.create({
    owner: issueParam.owner,
    repo: issueParam.repo,
    title: issueParam.title,
  });
};

export { createGithubIssue };
