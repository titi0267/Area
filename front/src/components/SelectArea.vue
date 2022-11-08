<template>
  <div id="SelectArea">
    <div v-for="service in services" :key="service.name">
        <div class="selected-service" v-if="area[type + 'ServiceId'] == service.id" :style="{ 'background-color': service.backgroundColor }">
            <h2>Select your {{ type }} type</h2>
            <b-image :src="$store.state.serveurURL + service.imageUrl"></b-image>
        </div>
        <div class="areas" v-if="service.id == area[type + 'ServiceId']">
            <div class="area"
                :style=" { 'background' : `linear-gradient(to top left, ${service.backgroundColor}, ${service.backgroundColor})` }"
                :class="{ selected: actrea.id == area[type + 'Id'] }"
                v-for="actrea in service[type + 's']"
                :key="actrea.name"
                @click="$emit(type + 'Id', actrea.id), $emit('save')">
                    <p class="title"> {{ actrea.name }} </p>
                    <p class="description"> {{ actrea.description }} </p>
            </div>
        </div>
    </div>
    <div class="buttons">
        <b-button @click="$emit('previous'), $emit('save')">
            Previous
        </b-button>
        <!-- <b-field v-if="area[type + 'Id'] != -1">
            <b-autocomplete
                class="param-input"
                ref="autocomplete"
                :data="searchInjectParams()"
                :placeholder="getParamName()"
                keep-first
                open-on-focus
                @select="option => selected = option"
                >
                <template #empty>No results for {{injectedParam}}</template>
            </b-autocomplete>
        </b-field> -->
        <b-button @click="$emit('next'), $emit('save'), $router.push(type == 'action' ? 'reaction' : 'overview')">
            Next
        </b-button>
    </div>
  </div>
</template>

<script lang="ts">
import vue from "vue";

export default vue.extend({
    data() {
        return {
            injectedParam: "", /** Current injected param name */
        };
    },
    props: {
        type: String /** Type between 'action' or 'reaction' */,
        services: Array /** Array that contains the About.JSON file */,
        area: Object /** Object that contains the area creation fields */,
        tokensTable: Object, /** Object that contains all oauth tokens of the user */
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
         * It's a function that returns the name
         * @data {Object} area
         * @data {Array} services
         */
        searchInjectParams() {
            let service = this.services.find(service => service.id == this.area[this.type + 'ServiceId']);
            let area: Object;
            if (service != undefined)
                area = service[this.type + 's'].find(actrea => actrea.id == this.area[this.type + 'Id']).availableInjectParams;
            return area;
        },
        /**
         * It's a function that returns the name of the parameter of the action or reaction.
         * @data {Object} area
         * @data {Array} services
         * @data {String} type
         * @return {String} - Return the actual selected service name.
         */
        getParamName(): String {
            if (this.area[this.type + "Id"] == -1 || this.services[0] == null) return;
            let service = this.services.find(
                (service) => service.id == this.area[this.type + "ServiceId"]
            );
            let paramName = service[this.type + "s"].find(
                (actrea) => actrea.id == this.area[this.type + "Id"]
            )[this.type + "ParamName"];
            return paramName;
        },
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
            this.$nextTick(async(): Promise<void> => {
                let serviceOauthName: string = this.services.find(service => service.id == this.area[this.type + "ServiceId"])['oauthName'];
                if (serviceOauthName == null || this.tokensTable[serviceOauthName + 'Token'] != null) {
                    this.$emit("loading");
                    return;
                }
                const code: string = this.$route.query.code;
                let oauthParam = {}
                if (code == null || code == undefined && this.tokensTable[serviceOauthName + 'Token'] == null) {
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
                } catch {
                    this.$emit('previous');
                    this.$emit('save');
                    this.notification("Your authentification has failed", 'is-danger');
                }
            })
            this.$emit("loading");
        },
    }
});
</script>

<style scoped lang="scss">
#SelectArea {
    width: 100%;
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
    .buttons {
        display: flex;
        position: absolute;
        padding: 0px 30px;
        left: 0;
        width: 100%;
        bottom: 20px;
        justify-content: space-between;
        :deep(button) {
            width: 100px;
            span, a {
                color: hsl(0deg, 0%, 21%);
            }
        }
        .param-input {
            width: 300px;
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
