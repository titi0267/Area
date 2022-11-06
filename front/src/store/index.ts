import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token: "",
        serveurURL: `http://${process.env.VUE_APP_URL}:${process.env.VUE_APP_PORT}/`,
    },
    getters: {
        userToken: state => state.token,
    },
    mutations: {
        updateToken (state, newToken: string) {
            state.token = newToken;
        },
    },
    actions: {
    },
    modules: {
    }
})
