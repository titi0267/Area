# AREA Front
The Area front-end is composed in two parts.
It manages all the pages and interactive elements linked to this project.

## Summary
* [Languages](#languages)
* [Installation](#installation)
* [Environnement](#environnement)
* [Compilation](#compilation)
* [Folders, Components & Files](#folders-components--files)
* [General Information](#general-information)
    * [LocalStorage](#localstorage)
    * [Unit tests](#unit-tests)
* [Vue project](#vue-project)
    * [Axios](#axios)
    * [VueX](#vuex)
    * [Vue Router](#vue-router)
    * [Buefy](#buefy)
    * [Mixins](#mixins)

## Languages
[![Languages](https://skillicons.dev/icons?i=vue)](https://v2.vuejs.org/)
[![Languages](https://skillicons.dev/icons?i=ts)](https://www.typescriptlang.org/)
[![Languages](https://skillicons.dev/icons?i=html)](https://developer.mozilla.org/fr/docs/Web/HTML)
[![Languages](https://skillicons.dev/icons?i=scss)](https://sass-lang.com/)
[![Languages](https://skillicons.dev/icons?i=jest)](https://jestjs.io/en/)

## Installation
- `npm install` Installation of dependencies.

## Environnement

The environnement variables are used by the Vue project, they allow to set the sensitive values from an external file called `.end`.

If you run the project with Docker, you can set the environnement variable from at the root project in the `docker.env` file.
However, if you start the Vue server outside Docker, the environnement file is in `/front/.env`.

You do to fill the `.enf` file with the following values.

| Name | Description | Type | Default Value |
|:----|:------------|:-----|:------------------|
<span style="color: #FF5500; text-decoration: underline;">***APP VARIABLES***</span> | | | |
| **VUE_APP_URL** | Back-end server URL | `string` | *No default value* |
| **VUE_APP_PORT** | Back-end server PORT | `number` | *8080* |

## Compilation
Two methods to compile the frontend
- npm run build (`production`)
- npm run serve (`developement`)

## Folders, Components & Files

### /Views
- **Login.vue** : Login page
- **Register.vue** : Register page
- **Home.vue** : Home page of the user. A place where every actions and reactions are listed
- **Area.vue** : Actions and reactions creation page
- **NotFound.vue** : If a page does not exist, the user is redirected on this page

### /Components
- **SelectServices.vue** : Selection management of the services
- **SelectArea.vue** : Selection management of the actions and reactions
- **Overview.vue** : Final view when a new action - reaction is created

### /Public
- index.html: Injected Vuejs page
- favicon.ico: Webpage icon
    ### /assets
    - buefy.scss: Buefy import with his CSS

### App.vue
- Parent component of all the Vue application

# General information

## LocalStorage
The localStorage is a place where some user data are saved.
Here is a list of data saved
| Name | Description | Type |
|:----|:------------|:-----|
| **token** | user token | `string` |
| **area** | area creation | `objet` |
| **dark-mode** | dark mode actif | `boolean` |
| **google-oauth** | login with google oauth | `boolean` |
| **oauth** | current oauth service | `string` |

## Unit tests
The unit tests allow you to test each Vue file from the project.
- `npm run test:unit` Command to run the unit tests

# Vue project

## Axios
Axios is an HTTP client based on promises. It is able to make requests to an `API`.
The code just below is the configuration file of axios. This file principally contains the back-end URL requests from the .env file. The object `header` is sent to each request and allow the server to control the validity of the request.
The `Authorization` header allows to verify if the user is connected with a valid token. If this token is not valid, the user is automatically redirected to the login page.

Filepath : `front/src/axiosInstance.ts`
```ts
const axios_instance = axios.create({
    timeout: 1000,
    withCredentials: true,
    baseURL: `http://${process.env.VUE_APP_URL}:${process.env.VUE_APP_PORT}`,
    headers: {
        "Access-Control-Allow-Origin": 'http://localhost:8081',
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: store.getters.userToken || localStorage.getItem("usr-token") || "noToken",
    },
});
```

## VueX
VueX is the state manager of the project. It allows the stocking of some variables and a way to get them back from any component of the project.
The code just below is the state store architecture. It contains four objects : State, Getters, Mutations and Actions.

Filepath : `front/src/store/index.ts`
```ts
export default new Vuex.Store({
    state: {
        // type : Object | Function
        // description : store the state variable
    },
    getters: {
        // type : { [key: string]: Function(state) }
        // description : retrieve store state variable
        // accès : store.getters.myGetter
    },
    mutations: {
        // type : { [type: string]: Function}
        // description : mute store state variable
        // accès : store.commit('myFunc', newValue)
    },
    actions: {
        // type : { [type: string]: Function(context) }
        // description: actions can emit mutations
        // access : store.dispatch('myFunc')
    },
})
```

## Vue Router
Vue Router allows the front-end routes management. Each route is assigned to a component.
The example above, is taken from the code and shows you a route with the name `register`, with path `/register` and imported above the code, the component `Register`.
The last element called `meta` is an object that contains diverse information for each route.
In that case, the `meta` object serves to know if the route is protected with an authentification.

Filepath : `front/src/router/index.ts`
```ts
import Register from "../views/Register.vue";

const router = new VueRouter({
    routes: [
        {
            path: '/register',
            name: 'register',
            component: Register,
            meta: { requiresAuth: false }
        }
    ]
})
```
Each route is `protected` with a user login verification system.
While recovering the token in the localStorage of the user and in the store (VueX), a request is sent to the API.
If this request is validated with the user token, then the access to the route will be granted.
Otherwise, the user will be redirected to the login page.
The code bellow allows to make the necessary redirection if the token is false or non-existent.

Filepath : `front/src/router/index.ts`
```ts
router.beforeEach(async (to, from, next) => {
    let usrToken = localStorage.getItem('usr-token');
    if (usrToken != null)
        store.commit('updateToken', usrToken)
    if (to.meta && to.meta.requiresAuth == true) {
        if (usrToken === null) {
            router.push("login");
            return;
        }
        try {
            await axios.get('/users/areas', {
                headers: {
                    Authorization: usrToken,
                }
            })
        } catch (err) {
            router.push("login");
        }
    }
    next();
});
```
## Buefy
Buefy is a UI component library for VueJs.
We use for all graphic elements.
For example, it allows to replace the HTML tag `<input>` by `<b-input>` of the Buefy library with a CSS style.
Also, buefy use an icon manager called: Font Awesome.

It allows to add icons in certain circumstances, like an error or a validation pop-up...

Below you can see the initialization of Buefy, with the Font Awesome icon import, with their size.

Filepath : `front/src/main.ts`
```ts
Vue.use(Buefy, {
    defaultIconComponent: FontAwesomeIcon,
    defaultIconPack: 'fas',
    customIconPacks: {
        fas: {
            sizes: {
                default: 'xs',
                'is-small': '1x',
                'is-medium': '2x',
                'is-large': '3x'
            },
        },
    },
    css: true,
});
```

## Mixins
Mixins are used to set global functions.
For example, if you need to call a function in two differents file, you can use a mixin.

To add a new mixin, create a new file in the folder `front/src/mixins/` prtotoyped like this : `yourFileName.mixin.ts`
Export your mixin file from `front/src/mixins/index.ts`

Next, you can import your new mixin in the Vue project.
In `front/src/main.ts` add `Vue.mixin(myMixinImport)`
