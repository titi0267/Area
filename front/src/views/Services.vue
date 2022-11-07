<template>
    <div id="Services" v-if="user.tokensTable && services">
        <div v-for="service of services" :key="service.name + service.backgroundColor">
            <div class="service" v-if="service.oauthName != null" :style="{ 'background-color' : service.backgroundColor }">
                <div class="left">
                    <b-image :src="$store.state.serveurURL + service.imageUrl"></b-image>
                    <p>{{ service.name }}</p>
                </div>
                <b-button v-if="user.tokensTable[service.oauthName + 'Token'] != null" @click="deleteServiceLink(service)" type="is-danger"> Sign out </b-button>
                <b-button v-else @click="getOAuthUrl(service)" type="is-success"> Sign in </b-button>
            </div>
        </div>
        <b-loading :is-full-page="true" v-model="loading"/>
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
            loading: false,
        }
    },
    mounted() {
        this.getAbout();
        this.getUserInfos();
        localStorage.removeItem('area');
        this.postOauth();
    },
    methods: {
        deleteServiceLink(service): void {
            try {
                let toRemove: Object;
                if (service.oauthName == 'discord') {
                    toRemove = {
                        [service.oauthName + 'Token'] : true,
                        [service.oauthName + 'GuildId'] : true,
                    }
                } else {
                    toRemove = {
                        [service.oauthName + 'Token'] : true,
                    }
                }
                let test = this.$axios.put('/tokens/delete', toRemove);
                console.log(test)
                this.$set(this.user.tokensTable, service.oauthName + 'Token', null);
            } catch {

            }
        },
        /**
         * It's a function that check if the user is authenticated and post the oauth code to the server.
         * @data {Object} area
         * @data {Array} services
         * @data {String} type
         * @data {Object} tokensTable
         * @async
         */
        postOauth(): void {
            this.loading = true
            this.$nextTick(async(): Promise<void> => {
                let serviceOauthName = localStorage.getItem('oauth');
                if (serviceOauthName == null) {
                    localStorage.removeItem('oauth');
                    this.loading = false;
                    return;
                }
                const code: String = this.$route.query.code;
                let oauthParam: Object;
                if (code == null || code == undefined) {
                    localStorage.removeItem('oauth');
                    this.loading = false;
                    this.notification("Your authentification has failed", 'is-danger');
                    return;
                }
                if (serviceOauthName == "discord") {
                    if ((typeof this.$route.query.permissions) !== "string") return;
                    oauthParam = {
                        code: code,
                        permissions: parseInt(this.$route.query.permissions),
                        guild_id: this.$route.query.guild_id
                    }
                } else {
                    oauthParam = {
                        code: code
                    }
                }
                try {
                    let {data: tokens} = await this.$axios.post("/oauth/" + serviceOauthName, oauthParam, {
                        headers: {
                            Authorization: this.$store.getters.userToken || "noToken",
                        }
                    });
                    this.$set(this.user.tokensTable, serviceOauthName + 'Token', tokens[serviceOauthName + 'Token']);
                    if (serviceOauthName == "discord") {
                        this.$set(this.user.tokensTable, serviceOauthName + 'GuildId', tokens[serviceOauthName + 'GuildId']);
                    }
                    this.notification("Your authentication to " + serviceOauthName + " was successful", 'is-success');
                } catch {
                    this.notification("Your authentification has failed", 'is-danger');
                }
                localStorage.removeItem('oauth');
                this.$router.replace({'query': null});
                this.loading = false;
                this.getUserInfos();
            })
        },
        /**
         * An async function that gets the oauth url for the service selected.
         * @data {Object} area
         * @data {Array} services
         * @data {Object} tokensTable
         * @data {String} type
         * @data {String} oauthURL
         * @async
         */
        async getOAuthUrl(service): Promise<void> {
            let serviceOauthName = service.oauthName;
            if (serviceOauthName == null) {
                this.notification('A problem occured, please select another ' + this.type, 'is-danger');
                return;
            }
            // this.loading = true;
            const { data: url } = await this.$axios.get("/oauth/" + serviceOauthName + "/link", {
            headers: {
                    Authorization: this.$store.getters.userToken || "noToken",
                },
            });
            localStorage.setItem('oauth', serviceOauthName);
            this.redirectOAuth(url);
        },
        /**
         * It's a function that redirects the user to the oAuth URL of the service selected.
         * @param {String} url
         */
        redirectOAuth(url: string): void {
            window.location.href = url;
        },
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
