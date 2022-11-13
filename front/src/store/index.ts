import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token: "",
        serveurURL: `http://${process.env.VUE_APP_URL}:${process.env.VUE_APP_PORT || 8080}/`,
        darkMode: false,
    },
    getters: {
        userToken: state => state.token,
        darkMode: state => state.darkMode
    },
    mutations: {
        updateToken (state, newToken: string) {
            state.token = newToken;
        },
        darkMode (state) {
            state.darkMode = !state.darkMode;
        }
    },
    actions: {
    },
    modules: {
    }
})
