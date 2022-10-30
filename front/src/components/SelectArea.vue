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
        <b-input class="param-input" @input="$emit(type + 'Param', $event)" :placeholder="getParamName()"></b-input>
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

        };
    },
    props: {
        type: String, /** Type between 'action' or 'reaction' */
        services: Array, /** Array that contains the About.JSON file */
        area: Object, /** Object that contains the area creation fields */
    },
    watch: {
        /**
         * This services watcher call the function when the services Array is not empty.
         */
        services: function () {
            this.$nextTick(() => this.postOAuthCode());
        },
    },
    methods: {
        /**
         * It's a function that returns the name of the parameter of the action or reaction.
         * @data {Object} area
         * @data {Array} services
         * @data {String} type
         * @return {String} - Return the actual selected service name.
         */
        getParamName() {
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
         * It's a function that post the oauth code to the server.
         * @data {Object} area
         * @data {Array} services
         * @data {String} type
         * @async
         */
        async postOAuthCode(): Promise<any> {
            const code: String = this.$route.query.code;
            if (code == null || code == undefined) return;
            let serviceIndex = -1;
            var servicesLength = await Object.keys(this.services).length;
            for (let i = 0; i < servicesLength; i++) {
                if (this.services[i].id == this.area[this.type + "ServiceId"])
                    serviceIndex = i;
                }
            if (serviceIndex == -1) return;
            let serviceName = this.services[serviceIndex].name;
            await this.$axios.post(
                "/oauth/" +
                    (serviceName === "Youtube" ? "google" : serviceName.toLowerCase()),
                {
                    code: code,
                }
            );
            this.$emit("loading");
        },
    }
});
</script>

<style scoped lang="scss">
#SelectArea {
    .selected-service {

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
