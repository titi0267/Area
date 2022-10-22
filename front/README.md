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

## Librairies
### Essentials
- vue
- vue-router
- vue-property-decorator
- vuex
- axios
- typescript
### Tools
- lodash
- vue-i18n
### Design
- buefy
- font-awesome-svg-core
- free-solid-svg-icon
- vue-fontawesome
- sass
- sass-loader

### Installation

1. `npm install` Installation des dependences.
2. `npm run build` Génère le dossier de build.
3. `npm run serve` En mode développement, permet d'avoir les mises à jour en temps réel.

### Variables d'environnement

| Nom | Description | Type | Valeur par défaut |
|:----|:------------|:-----|:------------------|
<span style="color: #FF5500; text-decoration: underline;">***APP VARIABLES***</span> | | | |
| **VUE_APP_URL** | URL du serveur pour le front | `string` | *Pas de valeur par défaut* |
| **VUE_APP_PORT** | Port du serveur pour le front | `number` | *8081* |
<span style="color: #FF5500; text-decoration: underline;">***OAUTH SERVICES VARIABLES***</span> | | | |
| **VUE_APP_GOOGLE_OAUTH_REDIRECT_URL** | Callback pour l'OAUTH Google | `url` | *Pas de valeur par défaut* |
| **VUE_APP_GOOGLE_CLIENT_ID** | ID du client Google | `id` | *Pas de valeur par défaut* |

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

### /Store
- index.ts: VuexStore pour les informations globales
    => State : pour la déclaration des variables
    => Getters : pour la récupération des éléments de state
    => Mutations : pour la modification des éléments de state

### /Public
- index.html: Page d'injection du VueJS
- favicon.ico: Icon de la page
    ### /assets
    - buefy.scss: Import de la librairie Buefy ainsi que son CSS

### App.vue
- Component parent de toute l'application.

# Utilisation des librairies

## Axios

## Buefy

## SASS (SCSS)

## Vue-I18n

## VueX

## Vue Router

Toutes les routes du routeur front se trouvent dans le dossier router/main.ts.
Elle permet de savoir si l'utilisateur doit se connecter, et le redirige vers la page necessaire de login.
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
