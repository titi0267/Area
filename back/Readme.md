# AREA: Back-end

## Summary :

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

## Languages :

[![Languages](https://skillicons.dev/icons?i=nodejs)](https://nodejs.org/en/)
[![Languages](https://skillicons.dev/icons?i=ts)](https://www.typescriptlang.org/)
[![Languages](https://skillicons.dev/icons?i=prisma)](https://www.prisma.io/)
[![Languages](https://skillicons.dev/icons?i=mysql)](https://mariadb.org/)
[![Languages](https://skillicons.dev/icons?i=jest)](https://jestjs.io/en/)

## Environnement :

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

## Dependency Installation :

```
npm i
```

## Useful commands :

```bash
npm run db:migrate # Init database
npm start # Launch the project in dev
npm run start:local # Launch the project in watch mode
npm build && node dist/src/index.js # Build the project and launch it
```

## API Documentation :

```
https://app.swaggerhub.com/apis-docs/ludovic-str/AREA/1.0.0
```

## Folder Organisation :

### /routes :

Project API endpoints

Example :

```ts
instance.get("/", async (req: FastifyRequest, res: FastifyReply) => {
  const users = await UserService.getAllUsers();

  res.status(httpStatus.OK).send(users);
});
```

### /services :

Functions that interact with the database mostly called in route files.

Example:

```ts
const getAllUsers = async (): Promise<User[]> => {
  return await prisma.user.findMany();
};
```

### /constants :

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

### /area :

Files linked to Action and Reaction: loop.area.ts contain the loop of launching action and reaction if action return true. Other folders are here to store actions and reactions.

Example:

### /helpers :

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

### /types :

Project typescript interfaces and types

```ts
interface Action {
  id: number;
  actionName: string;
  actionParamName: string;
  fct: (area: Area) => Promise<string | null>;
}
```

### /middlewares :

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

### /schema :

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
