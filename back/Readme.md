# AREA: Back-end

## Summary

- [Languages](#languages)
- [Environnement](#environnement)
- [Dependency Installation](#dependency-installation)
- [Useful commands](#useful-commands)
- [Api Documentation](#api-documentation)
- [Folder Organisation](#folder-organisation)
  - [/routes](#routes)
  - [/services](#services)
  - [/constants](#constants)
  - [/area](#area)
  - [/helpers](#helpers)
  - [/types](#types)
  - [/middlewares](#middlewares)
  - [/schema](#schema)
- [How to create a new Service](#how-to-create-a-new-service)
- [How to create an action](#how-to-create-an-action)
- [How to create a reaction](#how-to-create-a-reaction)

## Languages

[![Languages](https://skillicons.dev/icons?i=nodejs)](https://nodejs.org/en/)
[![Languages](https://skillicons.dev/icons?i=ts)](https://www.typescriptlang.org/)
[![Languages](https://skillicons.dev/icons?i=prisma)](https://www.prisma.io/)
[![Languages](https://skillicons.dev/icons?i=mysql)](https://mariadb.org/)
[![Languages](https://skillicons.dev/icons?i=jest)](https://jestjs.io/en/)

## Environnement

| Name                      | Description              | Type     | Default Value      |
| :------------------------ | :----------------------- | :------- | :----------------- |
| **DATABASE_URL**          | Database url format      | `string` | _No default value_ |
| **PORT**                  | Backend Port             | `string` | _8080_             |
| **SECRET**                | Password cripting secret | `string` | _No default value_ |
| **CLIENT_PORT**           | Port of the client       | `string` | _8081_             |
| **YOUTUBE_API_TOKEN**     | Youtube api token        | `string` | _No default value_ |
| **GOOGLE_CLIENT_SECRET**  | Google client secret     | `string` | _No default value_ |
| **GOOGLE_CLIENT_ID**      | Google client id         | `string` | _No default value_ |
| **GOOGLE_REDIRECT_URL**   | Google redirect url      | `string` | _No default value_ |
| **SPOTIFY_CLIENT_SECRET** | Spotify client secret    | `string` | _No default value_ |
| **SPOTIFY_CLIENT_ID**     | Spotify client id        | `string` | _No default value_ |
| **SPOTIFY_REDIRECT_URL**  | Spotify redirect url     | `string` | _No default value_ |
| **GITHUB_CLIENT_ID**      | Github client id         | `string` | _No default value_ |
| **GITHUB_REDIRECT_URL**   | Github redirect url      | `string` | _No default value_ |
| **DISCORD_BOT_TOKEN**     | Discord bot token        | `string` | _No default value_ |
| **DISCORD_CLIENT_ID**     | Discord client id        | `string` | _No default value_ |
| **DISCORD_CLIENT_SECRET** | Discord client secret    | `string` | _No default value_ |
| **DISCORD_REDIRECT_URL**  | Discord redirect url     | `string` | _No default value_ |

## Dependency Installation

```
npm i
```

## Useful commands

```bash
npm run db:migrate # Init database
npm start # Launch the project in dev
npm run start:local # Launch the project in watch mode
npm build && node dist/src/index.js # Build the project and launch it
```

## API Documentation

```
https://app.swaggerhub.com/apis-docs/ludovic-str/AREA/1.0.0
```

## Folder Organisation

### /routes

Project API endpoints

Example :

```ts
instance.get("/", async (req: FastifyRequest, res: FastifyReply) => {
  const users = await UserService.getAllUsers();

  res.status(httpStatus.OK).send(users);
});
```

### /services

Functions that interact with the database mostly called in route files.

Example:

```ts
const getAllUsers = async (): Promise<User[]> => {
  return await prisma.user.findMany();
};
```

### /constants

Constants which don't need to be in .env here service list

Example:

```ts
const SERVICES: Service[] = [
  {
    id: 1,
    serviceName: "Youtube",
    imageUrl: "https://www.iconsdb.com/icons/preview/white/youtube-6-xxl.png",
    backgroundColor: "#FF0000",
    actions: [
      {
        id: 1,
        actionName: "NewVideoUploaded",
        actionParamName: "Channel Name",
        fct: YoutubeActions.checkUploadedVideo,
      },
      {
        id: 2,
        actionName: "NewLikeOnAVideo",
        actionParamName: "Video Id",
        fct: YoutubeActions.checkVideoLike,
      },
    ],
    reactions: [],
  },
];
```

### /area

Files linked to Action and Reaction: loop.area.ts contain the loop of launching action and reaction if action return true. Other folders are here to store actions and reactions.

Exemple Reaction:

```ts
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
```

### /helpers

Groups of utility functions for a given part of the program

Example:

```ts
const getActionFct = (actionServiceId: number, actionId: number) => {
  const actionService = SERVICES.find(
    service => service.id === actionServiceId,
  );

  if (actionService === undefined) return null;
  const action = actionService.actions.find(action => action.id === actionId);

  return action?.fct || null;
};
```

### /types

Project typescript interfaces and types

```ts
interface Action {
  id: number;
  actionName: string;
  actionParamName: string;
  fct: (area: Area) => Promise<string | null>;
}
```

### /middlewares

Functions that are executed before launching route function

Usage exemple:

```ts
instance.get(
  "/me",
  { onRequest: [authentificationMiddleware()] },
  async (req: FastifyRequest, res: FastifyReply) => {
    const userInfos = SecurityHelper.getUserInfos(req);

    const user = await UserService.getOneUser(userInfos.id);

    res.status(httpStatus.OK).send(user);
  },
);
```

### /schema

Body schemas for API endpoints

Example:

```ts
const loginBodySchema: JSONSchemaType<LoginBody> = {
  type: "object",
  properties: {
    email: { type: "string" },
    password: { type: "string" },
  },
  required: ["email", "password"],
  additionalProperties: false,
};
```

## How to create a new Service

### If your service require oauth

1. Generate your client id and client secret on the service website in developer part (ex: for google https://cloud.google.com) and the redirect url which the path on which you want to redirect on the front

2. Add your credentials to .env and so in src/env.ts like this:

```ts
  clientId: process.env.CLIENT_ID as string,
  clientSecret: process.env.CLIENT_SECRET as string,
  redirectUrl: process.env.REDIRECT_URL as string
```

3. Add token field in database schema in prisma/schema.prisma like this:

```
  serviceToken      String?
```

Then run the following command

```bash
  npm run db:migrate
```

4. Create a service to save your token in the database like this:

```ts
const setServiceNameToken = async (
  userId: number,
  token: string | undefined | null,
): Promise<TokensTable> => {
  if (!token) {
    throw new ClientError({
      name: "Invalid Token",
      message: "Given token is invalid",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }

  const tokensTableExist = await prisma.tokensTable.findUnique({
    where: { userId },
  });

  if (!tokensTableExist) {
    // this part has for goal to check if the user exist
    throw new ClientError({
      name: "Invalid Credential",
      message: "UserId does not exist",
      level: "warm",
      status: httpStatus.BAD_REQUEST,
    });
  }
  // this part set the token in the database
  const tokenTable = await prisma.tokensTable.update({
    where: {
      userId,
    },
    data: {
      serviceToken: token,
    },
  });

  return tokenTable;
};
```

5. Add oauth service name in OauthService type in src/types/areaServices/areaServices.types.ts. Create oauth routes in src/routes/oauth.route.ts, one for sending the oauth link to the front like this:

```ts
instance.get("/serviceName/link", (req: FastifyRequest, res: FastifyReply) => {
  const rootUrl = "https://serviceName.com/api/oauth2/authorize"; // The base services oauth url are usually in this format but not always

  const option = {
    client_id: ENV.ClientId, // client id previously set
    response_type: "code",
    redirect_uri: ENV.redirectUrl, // redirect url previously set
    scope: [
      // scope you want to access to on the application, specific scope are documented on the service website
      "identify",
      "email",
      "guilds",
      "connections",
      "bot",
      "guilds.join",
    ].join(" "),
  };

  const qs = new URLSearchParams(option);

  res.status(httpStatus.OK).send(`${rootUrl}?${qs.toString()}`);
});
```

and one to save the route in your database like this:

```ts
instance.post(
  "/serviceName",
  { onRequest: [authentificationMiddleware()] },
  async (req: BaseOauthBody, res: FastifyReply) => {
    if (!googleOauthQueryValidator(req.body)) ErrorHelper.throwBodyError();

    const userInfos = SecurityHelper.getUserInfos(req);

    const code = req.body.code;

    // This part is an example with google API using the google npm library, some service have an oauth npm library and one some other you have to make a request on a specific endpoint
    const oauthClient = new google.auth.OAuth2(
      ENV.googleClientId,
      ENV.googleClientSecret,
      ENV.googleRedirectUrl,
    );

    const tokens = (await oauthClient.getToken(code)).tokens;

    const tokenTable = await TokenService.setGoogleToken(
      userInfos.id,
      tokens.refresh_token,
    );

    res.status(httpStatus.OK).send(tokenTable);
  },
);
```

6. Add service name at ServiceName type in src/types/areaServices/areaServices, the service logo in assets/types.ts and then add the service to src/constants/serviceList.ts with the following format:

```ts
  id: 1,
  serviceName: "serviceName",
  backgroundColor: "#DE5145", // background color to be displayed in the front
  imageUrl: "assets/serviceName.png",  // path to image to be displayed in the front
  oauthName: "serviceName", // name of the oauth service
  actions: []
  reactions: []
```

### If your service does not require oauth

1. Add service name at ServiceName type in src/types/areaServices/areaServices, the service logo in assets/types.ts and then add the service to src/constants/serviceList.ts with the following format:

```ts
  id: 1,
  serviceName: "serviceName",
  backgroundColor: "#DE5145", // background color to be displayed in the front
  imageUrl: "assets/serviceName.png",  // image to be displayed in the front
  oauthName: null, // name of the oauth service
  actions: []
  reactions: []
```
## How to create an Action

### Things to do if your service has no actions

1. Create a folder with your serviceName if it doesn't exist

2. Create a file named serviceName.action.ts

3. Create a function in src/helpers/service.helpers.ts to get your oauth client or your service credentials. Here is an example with google:

```ts
onst getGoogleOauthClient = async (
  userId: number,
): Promise<OAuth2Client | null> => {
  const refreshToken = await TokenService.getGoogleToken(userId); // get token from database

  if (!refreshToken) return null;

  const oAuth2Client = new google.auth.OAuth2(
    ENV.googleClientId,
    ENV.googleClientSecret,
    ENV.googleRedirectUrl,
  ); // configure google oauth client

  oAuth2Client.setCredentials({ refresh_token: refreshToken }); // set token on google oauth client

  return oAuth2Client;
};
```

### Action creation

1. Create a fonction prototyped as follow:

```ts
(area: Area) => Promise<string | null>;
```

2. Use the helper function you previously created or the one that already exist to get your credential if your service needs oauth

3. Use your service API endpoint or client to get the information you want. If any information you get is invalid return null

4. If it's the first time you pass in the function you have to set the value you want to compare with in futures action check. This can be done like this:

```ts
if (area.lastActionValue === null) {
  await AreaService.updateAreaValues(area.id, value);
  return null;
}
```

5. You can define a params object which contains data available to be injected in the reaction paramater by a user
   Here is a params object example:

```ts
const params = {
  like: statistics.likeCount,
  viewCount: statistics.viewCount,
};
```

6. For all the other time you pass into this function you will have to check if the default value you've set has changed. Here is an example with params:

```ts
if (value > area.lastActionValue) {
  await AreaService.updateAreaValues(area.id, value);
  return ServiceHelper.injectParamInReaction<typeof params>(
    area.reactionParam,
    params,
  );
}
```

Here is an example without params:

```ts
if (value > area.lastActionValue) {
  await AreaService.updateAreaValues(area.id, value);
  return area.reactionParam;
}
```

7. Finally, in the file src/constants/serviceList.ts add your action to actions array in your service in this format:

```ts
{
  id: 1,
    actionName: "New file in liked video", // Name of your action
    actionParamName: "", // Param that your action can take
    paramFormat: null, // if their is a format that your param have to match
    description: "New file in your drive", // A short description of your action
    fct: DriveAction.newLikedVideo, // The function of your action
    availableInjectParams: ["like"], // params which can be injected in the reaction, if their is not it will be empty
}
```

## How to create a Reaction

### Things to do if your service has no reactions

1. Create a folder with your serviceName if it doesn't exist

2. Create a file named serviceName.reaction.ts

3. Create a function in src/helpers/service.helpers.ts to get your oauth client or your service credentials. Here is an example with google:

```ts
onst getGoogleOauthClient = async (
  userId: number,
): Promise<OAuth2Client | null> => {
  const refreshToken = await TokenService.getGoogleToken(userId); // get token from database

  if (!refreshToken) return null;

  const oAuth2Client = new google.auth.OAuth2(
    ENV.googleClientId,
    ENV.googleClientSecret,
    ENV.googleRedirectUrl,
  ); // configure google oauth client

  oAuth2Client.setCredentials({ refresh_token: refreshToken }); // set token on google oauth client

  return oAuth2Client;
};
```

### Action creation

1. Create a fonction prototyped as follow:

```ts
(reactionParam: string, userId: number) => Promise<void>;
```

2. Use the helper function you previously created or the one that already exist to get your credential if your service need oaut

3. Use your service API endpoint or execute the reaction you want. If there is any problem with the data you get simply return.

4. Finally, in the file src/constants/serviceList.ts add your reaction to reactions array in your service in this format:

```ts
{
  id: 1,
  reactionName: "Create an issue", // Reaction name
  reactionParamName: "Issue infos (format: /owner/repo/issueTitle)", // Reaction param, format can be precised in it
  paramFormat: FORMAT.githubIssueFormat, // Reaction format check regex, can be null if there isn't
  fct: GithubReaction.createGithubIssue, // Reaction Function
  description:
    "Create an issue on a public repository or a private repository on which you belong",  // A short description of your reaction
},
```
