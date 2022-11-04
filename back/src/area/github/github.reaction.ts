import * as ServiceHelper from "../../helpers/service.helpers";

const createGithubIssue = async (reactionParam: string, userId: number) => {
  const githubClient = await ServiceHelper.getGithubClient(userId);

  if (!githubClient) return;

  const repo = await githubClient.rest.repos.get({
    owner: "ludovic-str",
    repo: "test",
  });

  console.log(repo);
};

export { createGithubIssue };
