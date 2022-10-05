import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'

import i18n from './lang/i18n'
import Buefy from 'buefy'
import "buefy/dist/buefy.css";
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

Vue.config.productionTip = false

Vue.component('vue-fontawesome', FontAwesomeIcon)
library.add(fas);

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

Vue.prototype.$axios = axios.create({
    timeout: 1000,
    withCredentials: true,
    // baseURL: '',
    headers: {
        "Access-Control-Allow-Origin": "http://localhost:8080",
        Accept: "application/json",
        "Content-Type": "application/json",
    },
});

new Vue({
    i18n,
    router,
    store,
    render: h => h(App)
}).$mount('#app')
