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
            @click="$emit(type + 'ServiceId', service.id), $emit('save')"
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
  mounted() {
    this.getGoogleOauthUrl();
  },
  props: {
    type: String,
    services: Array,
    area: Object,
  },
  components: {},
  methods: {
    debounceInput: _.debounce(function (input) {
      this.filterInput = input;
    }, 400),
    async getGoogleOauthUrl() {
      try {
        const { data: url } = await this.$axios.get("/oauth/Youtube", () => {});
        console.log(url);
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
