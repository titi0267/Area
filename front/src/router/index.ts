import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import NotFound from '../views/NotFound.vue'
import UserPannel from '../views/UserPannel.vue'
import CreateArea from '../views/CreateArea.vue'
import axios from '../axiosInstance';

Vue.use(VueRouter)

const router = new VueRouter({
    mode: 'history',
    base: '/',
    routes: [
        {
            path: '/register',
            name: 'register',
            component: Register,
            meta: { requiresAuth: false }
        },
        {
            path: '/login',
            name: 'login',
            component: Login,
            meta: { requiresAuth: false }
        },
        {
            path: '/user-pannel',
            name: 'userPannel',
            component: UserPannel,
            meta: { requiresAuth: false } // Mettre à true dès que le login sera fait et le middleware de co
        },
        {
            path: '/create-area',
            name: 'createArea',
            component: CreateArea,
            meta: { requiresAuth: false } // Mettre à true dès que le login sera fait et le middleware de co
        },
        {
            path: '/:pathMatch(.*)*',
            name: 'notFound',
            component: NotFound,
            meta: { requiresAuth: false }
        },
    ]
})

router.beforeEach(async (to, from, next) => {
    if (to.meta && to.meta.requiresAuth == true) {
        let usrToken = localStorage.getItem('usr-token');
        if (usrToken === null)
            router.push("login");
        try {
            let {data: resp} = await axios.get('/users', { // mettre une route qui nécessite un header "Autorization"
                headers: {
                    Authorization: usrToken || 'noToken',
                },
            })
            // check si la valeur de retour de axios est ok ou non grâce au token
            // si c'est ok => redirection vers la page user-pannel
            // sinon redirection vers le login
            router.push("user-pannel");
            next();
        } catch (err) {
            router.push("login");
        }
    }
    next();
});

export default router
