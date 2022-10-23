# AREA Front

Le côté client de l'area se compose en une partie.
Il gère toutes les pages et éléments interactif lié à ce projet.

## Sommaire
* [Langages](#langages)
* [Librairies](#librairies)
* [Installation](#installation)
* [Variables d'environnement](#variables-denvironnement)
* [Compilation](#compilation)
* [Les dossiers](#les-dossiers)

## Langages
[![Languages](https://skillicons.dev/icons?i=vue)](https://v2.vuejs.org/)
[![Languages](https://skillicons.dev/icons?i=ts)](https://www.typescriptlang.org/)
[![Languages](https://skillicons.dev/icons?i=html)](https://developer.mozilla.org/fr/docs/Web/HTML)
[![Languages](https://skillicons.dev/icons?i=scss)](https://sass-lang.com/)

## Installation
1. `npm install` Installation des dependences.
2. `npm run build` Génère le dossier de build.
3. `npm run serve` En mode développement, permet d'avoir les mises à jour en temps réel.

## Variables d'environnement
| Nom | Description | Type | Valeur par défaut |
|:----|:------------|:-----|:------------------|
<span style="color: #FF5500; text-decoration: underline;">***APP VARIABLES***</span> | | | |
| **VUE_APP_URL** | URL du serveur pour le front | `string` | *Pas de valeur par défaut* |
| **VUE_APP_PORT** | Port du serveur pour le front | `number` | *8081* |
<span style="color: #FF5500; text-decoration: underline;">***OAUTH SERVICES VARIABLES***</span> | | | |
| **VUE_APP_GOOGLE_OAUTH_REDIRECT_URL** | Callback pour l'OAUTH Google | `?` | *Pas de valeur par défaut* |
| **VUE_APP_GOOGLE_CLIENT_ID** | ID du client Google | `?` | *Pas de valeur par défaut* |
| **VUE_APP_SPOTIFY_OAUTH_REDIRECT_URL** | Callback pour l'OAUTH Spotify | `?` | *Pas de valeur par défaut* |
| **VUE_APP_SPOTIFY_CLIENT_ID** |  | `?` | *Pas de valeur par défaut* |
| **VUE_APP_SPOTIFY_CLIENT_SECRET** |  | `?` | *Pas de valeur par défaut* |
| **VUE_APP_GITHUB_OAUTH_REDIRECT_URL** |  | `?` | *Pas de valeur par défaut* |
| **VUE_APP_GITHUB_CLIENT_ID** |  | `?` | *Pas de valeur par défaut* |
| **VUE_APP_GITHUB_CLIENT_SECRET** |  | `?` | *Pas de valeur par défaut* |

## Compilation
Deux méthodes pour compiler le front
- npm run build (`production`)
- npm run serve (`developpement`)

## Les dossiers

### /Views
- **Login.vue** : Page de connexion de l'utilisateur
- **Register.vue** : Page d'inscription de l'utilisateur
- **Home.vue** : Page de principale de l'utiisateur. Il peut visualiser ses actions-réactions
- **Area.vue** : Page de création d'action-réaction
- **NotFound.vue** : Si une page n'éxiste pas, l'utilisateur est redirigé sur cette page

### /Components
- **SelectServices.vue** : Gestion de la selection des services
- **SelectArea.vue** : Gestion de la selection des actions et des réactions
- **Overview.vue** : Visualisation de l'action - réaction crée par l'utilisateur à la fin d'une création

### /Public
- index.html: Page d'injection du VueJS
- favicon.ico: Icon de la page
    ### /assets
    - buefy.scss: Import de la librairie Buefy ainsi que son CSS

### App.vue
- Component parent de toute l'application.

# Informations générale

## LocalStorage

# Utilisation des librairies

## Axios
Axios est un client HTTP basé sur des Promesses. Il permet de faire des requêtes à une API.
Ci-dessous le fichier de configuration de axios. Ce fichier contient principalement l'URL des requêtes côté serveur depuis le fichier .env. L'objet headers est envoyé à chaque requête et permet au côté serveur de contrôler la validité de la requête.
Le header 'Authorization' permet de vérifier si l'utilisateur est connecté avec un token valide. Si ce token n'est pas valide, l'utilisateur est automatique redirigé vers la page de login

Chemin du fichier : `front/src/axiosInstance.ts`
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
VueX est le gestionnaire d'état du projet. Il permet de stocker des variables et de les récupérer de n'importe quel composant du projet.
Ci-dessous se trouve l'architecture du magasin d'état. Il contient quatre objets : State, Getter, Mutation et Action.

Chemin du fichier : `front/src/store/index.ts`
```ts
export default new Vuex.Store({
    state: {
        // type : Object | Function
        // fonction : stocker l'objet d'état du magasin
    },
    getters: {
        // type : { [key: string]: Function(state) }
        // fonction : récupérer la valeur d'un objet d'état du magasin
        // accès : store.getters.monGetter
    },
    mutations: {
        // type : { [type: string]: Function}
        // fonction : muter la valeur d'un objet d'état du magasin
        // accès : store.commit('maFonction', newValue)
    },
    actions: {
        // type : { [type: string]: Function(context) }
        // fonction: les actions émettent des mutations
        // accès : store.dispatch('maFonction')
    },
})
```

## Vue Router
Vue Router permet la gestion des routes du côté client. Chaque route est assigné à un composant.
L'example ci-dessous tiré du code montre une route du nom de 'register' avec comme chemin d'accès '/register' et comme composant 'Register'. Ces informations permettent de relier la route '/register' au composant 'Register' importé plus haut.
Le dernière élément du nom de 'meta' est un object qui peut contenir diverses informations pour chaque route.
Dans ce cas, l'objet 'meta' sert à savoir si la route est protégé par une authentification.

Chemin du fichier : `front/src/router/index.ts`
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
Chaque route est protégé par un système de vérification de connection de l'utilisateur.
En récupérant le token dans le localStorage de l'utilisateur et le store, une requête est éxécutée à l'API.
Si cette requête est validé avec le token de l'utilisateur, alors nous l'accès lui sera accordé.
Dans le cas contraire, son accès sera redirigé vers la page de login.
Le code ci-dessous permet de faire les redirections necessaires si le token est faux ou inexistant.

Chemin du fichier : `front/src/router/index.ts`
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
## Vue-I18n
Vue-I18n est utilisé pour la gestion des langues sur l'interface utilisateur.
Deux choix s'offrent à lui, l'Anglais ou le Français.
Ci-dessous se trouve la déclaration de VueI18n, il permet de définir la langue par défaut qui est l'anglais.
L'objet message contient toutes les langues importé plus haut.

Chemin du fichier : `front/src/lang/i18n.ts`
```ts
const en = require("./en.json");
const fr = require('./fr.json');

export default new VueI18n({
    locale: "en-US",
    fallbackLocale: ['en-US'],
    messages: {
        "en-US": en,
        "fr-FR": fr,
    }
});
```
Les langues sont importées sous la forme d'un .JSON, chaque langue a son propre fichier.
Le fichier ci-dessous contient les mots français.

Chemin du fichier : `front/src/lang/fr.json`
```json
{
    "login": {
        "firstName": "Nom",
        "lastName": "Prénom",
    }
}
```
L'éxample ci-dessous illustre comment accèder à ces informations dans le code html.
Le résultat interprété sera : "Nom".
```html
 <p> {{ $t('login.firstName') }} </p>
```
## Buefy
Buefy est une bibliothèque de composants d'interface utilisateur pour VueJs.
Nous l'utilison pour tous les éléments graphique de base.
Il permet par exemple de remplacer les balises `<input>` classique par des balises `<b-input>` de la librairie buefy qui ont déjà un style css.
Buefy utilise un gestionnaire d'icon appelé: Font Awesome.

Il permet d'ajouter des icons dans certaines circonstances, tel que des erreurs, des pop-up de validation...

Ci-dessous on retrouve l'initialisation de Buefy, avec l'import des logo Font Awesome ainsi que leurs taille d'affichage.

Chemin du fichier : `front/src/main.ts`
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

# Tests unitaires