import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import axios from "../axiosInstance";
import store from "../store";

import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import NotFound from "../views/NotFound.vue";
import Home from "../views/Home.vue";
import Area from "../views/Area.vue";
import Profile from "../views/Profile.vue";
import Services from "../views/Services.vue";
import Edit from "../views/Edit.vue"

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
            path: '/create/action',
            name: 'create-action',
            component: Area,
            meta: { requiresAuth: true }
        },
        {
            path: '/create/reaction',
            name: 'create-reaction',
            component: Area,
            meta: { requiresAuth: true }
        },
        {
            path: '/create/overview',
            name: 'create-overview',
            component: Area,
            meta: { requiresAuth: true }
        },
        {
            path: '/edit/:id',
            name: 'edit',
            component: Edit,
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
