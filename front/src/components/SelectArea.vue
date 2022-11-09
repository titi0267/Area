<template>
  <div id="SelectArea">
    <b-icon class="previous" icon="chevron-right" @click.native="$emit('previous'), $emit('save')"></b-icon>
    <div v-for="service in services" :key="service.name">
        <div class="selected-service" v-if="area[type + 'ServiceId'] == service.id" :style="{ 'background-color': service.backgroundColor }">
            <h2>Select your {{ type }} type</h2>
            <b-image :src="$store.state.serveurURL + service.imageUrl"></b-image>
        </div>
        <div class="areas" v-if="service.id == area[type + 'ServiceId']">
            <div class="area"
                :style=" { 'background' : `linear-gradient(to top left, ${service.backgroundColor}, ${service.backgroundColor})` }"
                v-for="actrea in service[type + 's']"
                :key="actrea.id"
                @click="$emit(type + 'Id', actrea.id), $emit('save'), popUpIsOpen = true">
                    <p class="title"> {{ actrea.name }} </p>
                    <p class="description"> {{ actrea.description }} </p>
            </div>
        </div>
    </div>
    <div v-if="popUpIsOpen">
        <ParamPopUp
            :services="services"
            :area="area"
            :type="type"
            @close="popUpIsOpen = false"
            @next="$emit('next'), $emit('save'), $router.push(type == 'action' ? 'reaction' : 'overview'), popUpIsOpen = false"
            @save="$emit('save')"
        />
    </div>
  </div>
</template>

<script lang="ts">
import vue from "vue";
import ParamPopUp from "./ParamPopUp.vue";

export default vue.extend({
    data() {
        return {
            injectedParam: "", /** Current injected param name */
            popUpIsOpen: false, /** If the pop-up param is open the value will be true */
        };
    },
    props: {
        type: String /** Type between 'action' or 'reaction' */,
        services: Array /** Array that contains the About.JSON file */,
        area: Object /** Object that contains the area creation fields */,
        tokensTable: Object, /** Object that contains all oauth tokens of the user */
    },
    components: {
        ParamPopUp,
    },
    mounted() {
        this.checkAlreadyOAuth();
    },
    watch: {
        /**
         * It's a function that is call when the tokensTable is fill or when the component is load.
         */
        'services': function(): void {
            this.postOAuthCode();
        },
    },
    methods: {
        /**
         * It's a function that check if the user is already authenticated with the oAuth.
         * @data {Array} services
         * @data {Object} area
         * @data {Object} tokensTable
         */
        checkAlreadyOAuth(): void {
            this.$nextTick((): void => {
                let serviceOauthName: string = this.services.find(service => service.id == this.area[this.type + "ServiceId"]);
                if (serviceOauthName == null) return;
                if (this.tokensTable[serviceOauthName['oauthName'] + 'Token'] != null) {
                    this.$emit("loading");
                    return;
                }
            })
        },
        /**
         * It's a function that check if the user is authenticated and post the oauth code to the server.
         * @data {Object} area
         * @data {Array} services
         * @data {String} type
         * @data {Object} tokensTable
         * @async
         */
        postOAuthCode(): void {
            if (this.area[this.type + 'ServiceId'] != -1) {
                return;
            }
            this.$nextTick(async(): Promise<void> => {
                let serviceOauthName: string = this.services.find(service => service.id == this.area[this.type + "ServiceId"])['oauthName'];
                if (serviceOauthName == null || this.tokensTable[serviceOauthName + 'Token'] != null) {
                    this.$emit("loading");
                    return;
                }
                const code: String = this.$route.query.code;
                let oauthParam: object
                if ((code == null || code == undefined) && this.tokensTable[serviceOauthName + 'Token'] == null) {
                    this.$emit('previous');
                    this.$emit('save');
                    this.$emit('loading');
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
                    this.$set(this.tokensTable, serviceOauthName + 'Token', tokens[serviceOauthName + 'Token']);
                } catch (err) {
                    this.$emit('previous');
                    this.$emit('save');
                    this.notification("Your authentification has failed", 'is-danger');
                }
                this.$emit("loading");
                this.$emit("oauth")
            })
        },
    }
});
</script>

<style scoped lang="scss">
#SelectArea {
    width: 100%;
    .previous {
        position: absolute;
        transform: rotate(180deg);
        top: 120px;
        left: 30px;
        cursor: pointer;
    }
    .selected-service {
        display: flex;
        justify-content: center;
        flex-direction: column;
        border-radius: 20px;
        width: 75%;
        align-items: center;
        padding: 15px;
        margin: auto;
        margin-top: 20px;
        :deep(figure) {
            img {
                height: 90px;
            }
        }
        h2 {
            margin-bottom: 10px;
            color: white;
            font-family: 'Courier New', Courier, monospace;
        }
    }
    .areas {
        margin: 10px;
        margin: 10px;
        display: flex;
        height: 100%;
        position: relative;
        justify-content: center;
        flex-wrap: wrap;
        .area {
            height: 200px;
            width: 200px;
            border-radius: 10px;
            margin: 10px;
            padding: 10px;
            cursor: pointer;
            .title {
                margin-bottom: 10px;
                text-transform: uppercase;
            }
            .description {
                font-size: 17px;
            }
            p {
                color: white;
                overflow-wrap: break-word;
                font-size: 20px;
                font-family: Hitmo Regular;
                white-space: normal;
                text-align: start;
            }
            &.selected:not(p) {
                outline: 2px solid black;
            }
        }
    }
}
</style>
