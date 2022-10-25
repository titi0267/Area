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
                getOauthUrl()
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
      filterInput: "",
      oauthURL: "",
    };
  },
  props: {
    type: String,
    services: Array,
    area: Object,
  },
  components: {},
  watch: {},
  computed: {},
  methods: {
    debounceInput: _.debounce(function (input) {
      this.filterInput = input;
    }, 400),
    async getOauthUrl() {
      try {
        let serviceName = this.services.find(
          (service) => service.id == this.area[this.type + "ServiceId"]
        ).name;
        const { data: url } = await this.$axios.get(
          "/oauth/" +
            (serviceName == "Youtube" ? "google" : serviceName.toLowerCase()) +
            "/link/front"
        );
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
