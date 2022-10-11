# AREA Front

Le côté client de l'area se compose en une partie.
Il gère toutes les pages lié à ce projet.

### Langages
- VueJs
    => HTML, CSS (SCSS), TypeScript

### Installation

1. `npm install` Installation des dependences.
2. `npm run build` Génère le dossier pour l'export sur un serveur web.
3. `npm run serve` En mode développement, permet d'avoir les mises à jour en temps réel.

### Environnement

| Nom | Description | Type | Valeur par défaut |
|:----|:------------|:-----|:------------------|
| **VUE_APP_URL** | URL du serveur pour le front | `string` | *Pas de valeur par défaut* |
| **VUE_APP_PORT** | Port du serveur pour le front | `number` | *Pas de valeur par défaut* |

## Compilation

Deux méthodes pour compiler le front
- npm run build (`production`)
- npm run serve (`dev`)

Toutes les routes du routeur front se trouvent dans le dossier router/main.ts.
Elle permet de savoir si l'utilisateur doit se connecter, et le redirige vers la page necessaire de login.
```ts
router.beforeEach(async (to, from, next) => {
    let usrToken = localStorage.getItem('usr-token');
    if (usrToken != null)
        store.commit('updateToken', usrToken)
    if (to.meta && to.meta.requiresAuth == true) {
        if (usrToken === null)
            router.push("login");
        try {
            await axios.get('/users/areas');
            next();
        } catch (err) {
            router.push("login");
        }
    }
    next();
});
```

## Les dossiers

### /Views
- **Login.vue** : Page de connexion de l'utilisateur
- **Register.vue** : Page d'inscription de l'utilisateur
- **UserPannel.vue** : Page de visualisation des actions-réactions de l'utilisateur
- **CreateArea.vue** : Page de création d'action-réaction
- **NotFound.vue** : Si une page n'éxiste pas, l'utilisateur est redirigé sur cette page

### /Components
- 

### /Store
- index.ts: VuexStore pour les informations globales
    => State : pour la déclaration des variables
    => Getters : pour la récupération des éléments de state
    => Mutations : pour la modification des éléments de state

### /Public
- index.html: Page d'injection du VueJS

### App.vue
- Component parent de toute l'application.
