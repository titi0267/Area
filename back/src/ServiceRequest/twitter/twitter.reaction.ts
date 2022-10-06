import { TwitterApi } from "twitter-api-v2";

const postNewTweet = async (reactionParam: string, userId: string) => {
  const twitterClient = new TwitterApi("");

  await twitterClient.v2.tweet(reactionParam);
};

export { postNewTweet };
