import { TwitterApi } from "twitter-api-v2";

const postNewTweet = async (reactionParam: string, userId: string) => {
  console.log("Test");

  // const client = new TwitterApi({
  //   appKey: "k0rZehVJwtjOsvNc3SnWga5ZR",
  //   appSecret: "noOF8v7Rhx1QCqjPFG6Ist7xpbvvChmnVRKDbr9BZbkpt7a9Jo",
  //   accessToken: "1081919366621528064-Qw1BgFLgJTwxkyBgDIPHx2wPHrrGC9",
  //   accessSecret: "ox8l0fkUaueg8JVc7foCDyf7PlqvjM79pmFhFdCAdlnnk",
  // });
  // const rwClient = client.readWrite;

  // const test = await rwClient.v2.tweet("lol");
};

export { postNewTweet };
