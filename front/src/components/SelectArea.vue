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
    return {};
  },
  mounted() {
    this.$nextTick(() => {  
      this.postOauthCode();
    });
  },
  props: {
    type: String,
    services: Array,
    area: Object,
  },
  components: {},
  methods: {
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
    async postOauthCode() {
      try {
        const code = this.$route.query.code;
        console.log("le code = " + code);

        if (code == null || code == undefined) return;
        let serviceIndex = -1;
        var servicesLength = await Object.keys(this.services).length;
        console.log(
          "Service len = " + servicesLength + " & total = " + this.services
        );
        for (let i = 0; i < servicesLength; i++) {
          console.log(
            "Id = " +
              this.services[i].id +
              " & action = " +
              this.area[this.type + "ServiceId"]
          );
          if (this.services[i].id == this.area[this.type + "ServiceId"])
            serviceIndex = i;
        }
        console.log("Index = " + serviceIndex);
        if (serviceIndex == -1) return;
        let serviceName = this.services[serviceIndex].name;
        console.log("code = " + code);
        await this.$axios.post(
          "/oauth/" +
            (serviceName === "Youtube" ? "google" : serviceName.toLowerCase()),
          {
            code: code,
          }
        );
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
