import Vue from "vue";
import VueRouter, { Route, RouteConfig } from "vue-router";
import axios from "../axiosInstance";
import store from "../store";

import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import NotFound from "../views/NotFound.vue";
import Home from "../views/Home.vue";
import Area from "../views/Area.vue";
import Profile from "../views/Profile.vue";
import Services from "../views/Services.vue";
import Create from "../views/Create.vue"
import Apk from "../views/Apk.vue"

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
            path: "/client.apk",
            name: "client.apk",
            component: Apk,
            meta: { requiresAuth: false },
        },
        {
            path: '/home',
            name: 'home',
            component: Home,
            meta: { requiresAuth: true }
        },
        {
            path: '/create',
            name: 'create',
            component: Create,
            meta: { requiresAuth: true }
        },
        {
            path: '/create/action',
            name: 'action',
            component: Area,
            meta: { requiresAuth: true }
        },
        {
            path: '/create/reaction',
            name: 'reaction',
            component: Area,
            meta: { requiresAuth: true }
        },
        {
            path: '/create/overview',
            name: 'overview',
            component: Area,
            meta: { requiresAuth: true }
        },
        {
            path: '/profile',
            name: 'profile',
            component: Profile,
            meta: { requiresAuth: true }
        },
        {
            path: '/services',
            name: 'services',
            component: Services,
            meta: { requiresAuth: true }
        },
        {
            path: '/',
            redirect: '/home',
            meta: { requiresAuth: true }
        },
        {
            path: '/:pathMatch(.*)*',
            name: 'not-found',
            component: NotFound,
            meta: { requiresAuth: false }
        },
    ]
})

/**
 * A function that is called before each route is loaded to check if the user is correctly unauthenticated.
 * @constant
 * @name router
 * @type {VueRouter}
 */
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
