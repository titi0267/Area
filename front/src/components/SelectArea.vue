<template>
  <div id="SelectArea">
    <h2>Select your {{ type }} type</h2>
    <div>
      <div v-for="service in services" :key="service.name">
        <div class="selected-service" v-if="area[type + 'ServiceId'] == service.id">
            <b-image :src="service.imageUrl"></b-image>
            <p> {{ service.name }} </p>
        </div>
        <div class="areas" v-if="service.id == area[type + 'ServiceId']">
          <div class="area"
            :style=" { 'background' : `linear-gradient(to top left, ${service.backgroundColor}, ${service.backgroundColor})` }"
            :class="{ selected: actrea.id == area[type + 'Id'] }"
            v-for="actrea in service[type + 's']"
            :key="actrea.name"
            @click="$emit(type + 'Id', actrea.id), $emit('save')">
                <p> {{ actrea.name }} </p>
          </div>
        </div>
      </div>
    </div>
    <div class="buttons">
        <b-button @click="$emit('previous'), $emit('save')">
            Previous
        </b-button>
        <!-- <b-input class="param-input" @input="$emit(type + 'Param', $event)" :placeholder="getParamName()"></b-input> -->
        <b-field v-if="area[type + 'Id'] != -1">
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
        </b-field>
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
        'tokensTable': function(): void {
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
                if (this.tokensTable[serviceOauthName + 'Token'] != null) {
                    this.$emit("loading");
                    return;
                }
                const code: String = this.$route.query.code;
                if (code == null || code == undefined && this.tokensTable[serviceOauthName + 'Token'] == null) {
                    this.$emit('previous');
                    this.$emit('save');
                    this.$emit('loading');
                    this.notification("Your authentification has failed", 'is-danger');
                    return;
                }
                try {
                    let {data: tokens} = await this.$axios.post("/oauth/" + serviceOauthName, {
                        code: code,
                    }, {
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
    .selected-service {
        display: flex;
        justify-content: center;
    }
    h2 {
        font-family: 'Courier New', Courier, monospace;
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
        .area {
            height: 200px;
            width: 200px;
            border-radius: 10px;
            margin: 5px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            cursor: pointer;
            padding: 10px;
            p {
                overflow-wrap: break-word;
                font-size: 20px;
                font-family: Hitmo Regular;
                text-transform: uppercase;
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
