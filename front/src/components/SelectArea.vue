<template>
  <div id="SelectArea">
    <h2>Sélectionnez votre {{ type }}</h2>
    <div>
      <div v-for="service in services" :key="service.name">
        <div class="areas" v-if="service.id == area[type + 'ServiceId']">
          <div
            class="area"
            :class="{ selected: actrea.id == area[type + 'Id'] }"
            v-for="actrea in service[type + 's']"
            :key="actrea.name"
            @click="$emit(type + 'Id', actrea.id), $emit('save')"
          >
            {{ actrea.name }}
          </div>
        </div>
      </div>
    </div>
    <b-input
      @input="$emit(type + 'Param', $event)"
      :placeholder="getParamName()"
    ></b-input>
    <b-button @click="$emit('previous'), $emit('save')">Précédent</b-button>
    <b-button
      @click="
        $emit('next'),
          $emit('save'),
          $router.push(type == 'action' ? 'reaction' : 'overview')
      "
      >Suivant</b-button
    >
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
        getParamName(): String {
            if (this.area[this.type + "Id"] == -1 || this.services[0] == null) return;
            let service: Object = this.services.find((service) => service.id == this.area[this.type + "ServiceId"]);
            let paramName: String = service[this.type + "s"].find((actrea) => actrea.id == this.area[this.type + "Id"])[this.type + "ParamName"];
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
            try {
                const code: String = this.$route.query.code;

                if (code == null) return;
                let serviceName: String = this.services.find((service) => service.id == this.area[this.type + "ServiceId"]).name;
                await this.$axios.post("/oauth/" + (serviceName === "Youtube" ? "google" : serviceName.toLowerCase()), {
                    code: code,
                });
            } catch (e) {
                console.log(e);
            }
        },
    },
});
</script>

<style scoped lang="scss">
.areas {
  display: flex;
}

.area {
  border: 1px solid black;
  height: 150px;
  width: 150px;
  border-radius: 10px;
  cursor: pointer;
  &.selected {
    border: 3px solid black;
    background-color: green;
  }
}
</style>
