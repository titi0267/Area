# AREA: Back-end

## Dependency Installation :

```
npm i
```

## Start project :

```bash
npm run db:migrate # Init database
npm start # Launch the project in dev
npm run start:local # Launch the project in watch mode
npm build && node dist/src/index.js # Build the project and launch it
```

## Environnement :

| Nom                   | Description               | Type     | Valeur par défaut          |
| :-------------------- | :------------------------ | :------- | :------------------------- |
| **DATABASE_URL**      | URL de la base de donnée  | `string` | _Pas de valeur par défaut_ |
| **PORT**              | Port utilisé pour le back | `string` | _8080_                     |
| **SECRET**            | Password cripting secret  | `string` | _Pas de valeur par défaut_ |
| **YOUTUBE_API_TOKEN** | Youtube api token         | `string` | _Pas de valeur par défaut_ |
| **DISCORD_BOT_TOKEN** | Discord bot token         | `string` | _Pas de valeur par défaut_ |

## API Documentation :

```
https://app.swaggerhub.com/apis-docs/ludovic-str/AREA/1.0.0
```
