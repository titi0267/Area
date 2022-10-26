<template>
  <div id="SelectService">
    <h2>Sélectionnez le service de votre {{ type }}</h2>
    <div>
      <b-input @input="debounceInput"></b-input>
      <div class="services">
        <div v-for="service in services" :key="service.name">
          <div
            class="service"
            :class="{ selected: service.id == area[type + 'ServiceId'] }"
            v-if="
              service[type + 's'].length != 0 &&
              service.name.toLowerCase().includes(filterInput.toLowerCase())
            "
            @click="
              $emit(type + 'ServiceId', service.id),
                $emit('save'),
                getOAuthUrl()
            "
          >
            {{ service.name }}
          </div>
        </div>
      </div>
    </div>
    <b-button
      @click="
        $emit('previous'),
          $emit('save'),
          $router.push(area.state == -1 ? '/home' : '/create/action')
      "
      >Précédent</b-button
    >
    <b-button @click="$emit('next'), $emit('save')">
      <a :href="oauthURL">Suivant</a></b-button
    >
  </div>
</template>

<script scoped lang="ts">
import vue from "vue";
import _ from "lodash";

export default vue.extend({
  data() {
    return {
      filterInput: "", /** It's a filter input used for search a service */
      oauthURL: "", /** This variable contains the oAuth URL of the right service */
    };
  },
  props: {
    type: String, /** Type between 'action' or 'reaction' */
    services: Array, /** Array that contains the About.JSON file */
    area: Object, /** Object that contains the area creation fields */
  },
  methods: {
    /**
     * It's a function that waits for 400ms before executing the the input function.
     * @param {String} input - Text input
     * @data {String} filterInput
     */
    debounceInput: _.debounce(function (input) {
      this.filterInput = input;
    }, 400),
    /**
     * An async function that gets the oauth url for the service selected.
     * @data {Object} area
     * @data {Array} services
     * @data {String} type
     * @async
     */
    async getOAuthUrl(): Promise<any> {
      try {
        let serviceName = this.services.find(
          (service) => service.id == this.area[this.type + "ServiceId"]
        ).name;
        const { data: url } = await this.$axios.get("/oauth/" + (serviceName == "Youtube" ? "google" : serviceName.toLowerCase()) + "/link/front");
        this.oauthURL = url;
      } catch (e) {
        console.log(e);
      }
    },
  },
});
</script>

<style lang="scss">
.services {
  display: flex;
}

.service {
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
