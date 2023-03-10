import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from './axiosInstance'
import { Notifications } from './mixins'

import Buefy from 'buefy'
import "@/assets/buefy.scss"
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
            iconPrefix: "",
        },
    },
});

Vue.prototype.$axios = axios;

Vue.mixin(Notifications);

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
