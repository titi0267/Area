<template>
    <div id="Services" v-if="user.tokensTable && services">
        <div class="service" v-for="service of services" :key="service.name + service.backgroundColor">
            <div class="left">
                <b-image :src="$store.state.serveurURL + service.imageUrl"></b-image>
                <p>{{ service.name }}</p>
            </div>
            <b-button v-if="user.tokensTable[service.oauthName + 'Token'] != null" type="is-danger"> Sign out </b-button>
            <b-button v-else type="is-success"> Sign in </b-button>
        </div>
    </div>
</template>

<script scoped lang="ts">
import vue from 'vue';
import { Service } from '../types/index'

export default vue.extend({
    data() {
        return {
            services: [] as Service[], /** An array that will be filled with the about.json from the server. */
            user: [],
        }
    },
    mounted() {
        this.getAbout();
        this.getUserInfos()
    },
    methods: {
        /**
         * That function is used to get the infos of the user.
         * @data {Array} user
         * @async
         */
        async getUserInfos(): Promise<any> {
            let {data: user} = await this.$axios.get('/users/me', {
                headers: {
                    Authorization: this.$store.getters.userToken || "noToken",
                }
            })
            this.user = user
        },
        /**
         * A function that gets the about.json from the server.
         * @data {Array} services
         * @async
         */
        async getAbout(): Promise<void> {
            let { data: services } = await this.$axios.get("/about.json");
            this.services = services.server.services;
        },
    }
})
</script>

<style lang="scss" scoped>
#Services {
    padding: 20px;
    padding-top: 95px;
    .service {
        display: flex;
        margin-bottom: 10px;
        align-items: center;
        justify-content: space-between;
        border: 1px solid black;
        .left {
            display: flex;
            align-items: center;
        }
    }
}
</style>
