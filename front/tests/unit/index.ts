import Vue from 'vue'
import router from '@/router/index'
import store from '@/store/index'
import i18n from '@/lang/i18n'
import axios from '@/axiosInstance'

import Buefy from 'buefy'
import "../../public/assets/buefy.scss"
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

Vue.config.productionTip = false

Vue.component('vue-fontawesome', FontAwesomeIcon)
library.add(fas);

Vue.use(Buefy, {
    defaultIconComponent: FontAwesomeIcon,
    defaultIconPack: 'fas',
});

Vue.prototype.$axios = axios;

new Vue({
    i18n,
    router,
    store,
})
