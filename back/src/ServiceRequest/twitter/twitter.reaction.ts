import { TwitterApi } from "twitter-api-v2";

const postNewTweet = async (reactionParam: string, userToken: string) => {
  const twitterClient = new TwitterApi(userToken);

  await twitterClient.v2.tweet(reactionParam);
};

export { postNewTweet };
