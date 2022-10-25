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
  - [/schema](#schemag)

## Languages :

[![Languages](https://skillicons.dev/icons?i=nodejs)](https://nodejs.org/en/)
[![Languages](https://skillicons.dev/icons?i=ts)](https://www.typescriptlang.org/)
[![Languages](https://skillicons.dev/icons?i=prisma)](https://www.prisma.io/)
[![Languages](https://skillicons.dev/icons?i=mysql)](https://mariadb.org/)
[![Languages](https://skillicons.dev/icons?i=jest)](https://jestjs.io/en/)

## Environnement :

| Nom                       | Description               | Type     | Valeur par défaut          |
| :------------------------ | :------------------------ | :------- | :------------------------- |
| **DATABASE_URL**          | URL de la base de donnée  | `string` | _Pas de valeur par défaut_ |
| **PORT**                  | Port utilisé pour le back | `string` | _8080_                     |
| **SECRET**                | Password cripting secret  | `string` | _Pas de valeur par défaut_ |
| **CLIENT_PORT**           | Port of the client        | `string` | _Pas de valeur par défaut_ |
| **YOUTUBE_API_TOKEN**     | Youtube api token         | `string` | _Pas de valeur par défaut_ |
| **GOOGLE_CLIENT_SECRET**  | Google client secret      | `string` | _Pas de valeur par défaut_ |
| **GOOGLE_CLIENT_ID**      | Google client id          | `string` | _Pas de valeur par défaut_ |
| **GOOGLE_REDIRECT_URL**   | Google redirect url       | `string` | _Pas de valeur par défaut_ |
| **SPOTIFY_CLIENT_SECRET** | Spotify client secret     | `string` | _Pas de valeur par défaut_ |
| **SPOTIFY_CLIENT_ID**     | Spotify client id         | `string` | _Pas de valeur par défaut_ |
| **SPOTIFY_REDIRECT_URL**  | Spotify redirect url      | `string` | _Pas de valeur par défaut_ |
| **GITHUB_CLIENT_ID**      | Github client id          | `string` | _Pas de valeur par défaut_ |
| **GITHUB_REDIRECT_URL**   | Github redirect url       | `string` | _Pas de valeur par défaut_ |
| **DISCORD_BOT_TOKEN**     | Discord bot token         | `string` | _Pas de valeur par défaut_ |

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

### /services :

    Functions that interact with the database mostly called in route files.

### /constants :

    Constants which don't need to be in .env

### /area :

    Files linked to Action and Reaction: loop.area.ts contain the loop of launching action and reaction if action return true. Other folders are here to store actions and reactions.

### /helpers :

    Groups of utility functions for a given part of the program

### /types :

    Project typescript interfaces and types

### /middlewares :

    Functions that are executed before launching route function
    Example: Authentification to check if the user is logged in

## /schema :

    Body schemas for API endpoints
