import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import NotFound from "../views/NotFound.vue";
import UserPannel from "../views/UserPannel.vue";
import CreateArea from "../views/CreateArea.vue";
import axios from "../axiosInstance";
import store from "../store";

Vue.use(VueRouter);

const router = new VueRouter({
<<<<<<< HEAD
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
            meta: { requiresAuth: true }
        },
        {
            path: '/create-area',
            name: 'createArea',
            component: CreateArea,
            meta: { requiresAuth: true }
        },
        {
            path: '/',
            redirect: '/user-pannel',
            meta: { requiresAuth: false }
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
        if (usrToken === null)
            router.push("login");
        try {
            await axios.get('/users/areas')
            next();
        } catch (err) {
            router.push("login");
        }
    }
    next();
=======
  mode: "history",
  base: "/",
  routes: [
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: { requiresAuth: false },
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: { requiresAuth: false },
    },
    {
      path: "/user-pannel",
      name: "userPannel",
      component: UserPannel,
      meta: { requiresAuth: true },
    },
    {
      path: "/create-area",
      name: "createArea",
      component: CreateArea,
      meta: { requiresAuth: true },
    },
    {
      path: "/",
      redirect: "/user-pannel",
      meta: { requiresAuth: false },
    },
    {
      path: "/:pathMatch(.*)*",
      name: "notFound",
      component: NotFound,
      meta: { requiresAuth: false },
    },
  ],
>>>>>>> 458f15798e26c584d5de5fd1c510de65a20f7b82
});

router.beforeEach(async (to, from, next) => {
  let usrToken = localStorage.getItem("usr-token");
  if (usrToken != null) store.commit("updateToken", usrToken);
  if (to.meta && to.meta.requiresAuth == true) {
    if (usrToken === null) router.push("login");
    try {
      await axios.get("/users/areas");
      next();
    } catch (err) {
      router.push("login");
    }
  }
  next();
});

export default router;
