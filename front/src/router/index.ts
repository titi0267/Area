import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import NotFound from "../views/NotFound.vue";
import Home from "../views/Home.vue";
import CreateArea from "../views/CreateArea.vue";
import axios from "../axiosInstance";
import store from "../store";

Vue.use(VueRouter);

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
            path: '/home',
            name: 'home',
            component: Home,
            meta: { requiresAuth: true }
        },
        {
            path: '/create-tmp', // <-- C'est temporaire /!\
            name: 'createArea',
            component: CreateArea,
            meta: { requiresAuth: true }
        },
        {
            path: '/choose-action-service',
            name: 'chooseActionService',
            // component: ,
            meta: { requiresAuth: true }
        },
        {
            path: '/choose-reaction-service',
            name: 'chooseReactionService',
            // component: ,
            meta: { requiresAuth: true }
        },
        {
            path: '/choose-action',
            name: 'chooseAction',
            // component: ,
            meta: { requiresAuth: true }
        },
        {
            path: '/choose-reaction',
            name: 'chooseReaction',
            // component: ,
            meta: { requiresAuth: true }
        },
        {
            path: '/',
            redirect: '/home',
            meta: { requiresAuth: true }
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

export default router;
