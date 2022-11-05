<template>
    <div id="Services" v-if="user.tokensTable && services">
        <div class="service" v-for="service of services" :key="service.name + service.backgroundColor" :style="{ 'background-color' : service.backgroundColor }">
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
        this.getUserInfos();
        localStorage.removeItem('area');
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
    padding-top: 110px;
    .service {
        display: flex;
        margin-bottom: 10px;
        align-items: center;
        justify-content: space-between;
        height: 55px;
        border-radius: 15px;
        padding: 10px;
        :deep(.button) {
            width: 90px;
            height: 40px;
            box-shadow: 0 0 15px 1px rgb(0 0 0 / 33%);
        }
        .left {
            display: flex;
            align-items: center;
            p {
                font-family: "Avenir Roman";
                color: white;
                font-size: 20px;
            }
            :deep(figure) {
                margin-right: 15px;
                img {
                    height: 45px;
                }
            }
        }
    }
}
</style>
