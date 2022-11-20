<a>
    <img src="https://i.imgur.com/1GKYfwP.png" alt="AREA logo" title="AREA" align="right" height="60" />
</a>

# The AREA Project

AREA stands for Action REAction.

## What's AREA?

AREA is a software suite that have the objective to create links between Actions and Reactions from different services with the use of triggers.

It can be broken down into three parts:
- An application server
- A web client
- A mobile client

## Table of content
- [Getting started with AREA](#getting-started-with-area)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Launch](#launch)
- [Languages used for the project](#languages-used-for-the-project)

## Getting started with AREA

### Prerequisites

In order to use AREA, you must have Docker Compose installed on your computer ([Installation tutorial](https://docs.docker.com/compose/install/))

### Installation

1. Clone the repository.

    Run the following command:
    ```bash
    git clone git@github.com:EpitechPromo2025/B-DEV-500-STG-5-1-area-mathieu.brujan.git
    ```

2. Add your docker.env, key.properties and area-release-key.jks to the project.

   1. If you've been provided a EnvInjector.zip:
      1. Extract your EnvInjector.zip at the root of the repository.
      2. Run the following command:
         ```bash
         cd env_injector && sudo chmod 777 envInjector.sh && ./envInjector.sh
         ```
      3. Proceed to the next step.

    2. If you don't have a EnvInjector.zip:
       1. Contact one of the creator of the project in order to receive the zip file.

3. Build your project with Docker Compose.

    ```bash
    docker-compose build
    ```
   
### Launch

Launch the project with
    ```
    docker-compose up
    ```.

The application server will launch at http://localhost:8080.

The web client will launch at http://localhost:8081.

The mobile client will be downloadable at http://localhost:8081/client.apk.

## Languages used for the project

The application server:

[![Languages](https://skillicons.dev/icons?i=nodejs)](https://nodejs.org/en/)
[![Languages](https://skillicons.dev/icons?i=ts)](https://www.typescriptlang.org/)
[![Languages](https://skillicons.dev/icons?i=prisma)](https://www.prisma.io/)
[![Languages](https://skillicons.dev/icons?i=mysql)](https://mariadb.org/)
[![Languages](https://skillicons.dev/icons?i=jest)](https://jestjs.io/en/)

The web client:

[![Languages](https://skillicons.dev/icons?i=vue)](https://v2.vuejs.org/)
[![Languages](https://skillicons.dev/icons?i=ts)](https://www.typescriptlang.org/)
[![Languages](https://skillicons.dev/icons?i=html)](https://developer.mozilla.org/fr/docs/Web/HTML)
[![Languages](https://skillicons.dev/icons?i=scss)](https://sass-lang.com/)
[![Languages](https://skillicons.dev/icons?i=jest)](https://jestjs.io/en/)

The mobile client:

[![Languages](https://skillicons.dev/icons?i=kotlin)](https://kotlinlang.org/)
