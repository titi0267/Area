<template>
  <div>
    <div>
      <p>
        {{
          currentSelectedService.length == 0
            ? "Choose a service"
            : currentSelectedService.name
        }}
      </p>
    </div>
    <div v-for="service in services" :key="service.name">
      <div v-if="!serviceTypeStatus && service[type].length != 0">
        <b-button
          style="is-primary"
          @click="
            (serviceTypeStatus = true),
              (currentSelectedService = service),
              getChosenServiceType(service.name),
              saveServiceInfos(service.id)
          "
          >{{ service.name }}</b-button
        >
      </div>
      <div v-else-if="serviceTypeStatus">
        <div v-if="currentSelectedService.name == service.name">
          <div v-for="area in service[type]" :key="area.name">
            <b-button
              :type="area.id == currentSelectedArea.id ? 'is-success' : ''"
              @click="(currentSelectedArea = area), saveAreaInfos(area.id)"
              >{{ area.name }}</b-button
            >
          </div>
        </div>
      </div>
    </div>
    <div v-if="currentSelectedArea.id != -1">
      <b-input
        :placeholder="
          type == 'actions'
            ? currentSelectedArea.actionParamName
            : currentSelectedArea.reactionParamName
        "
        @input="saveInputInfos($event)"
      />
    </div>
  </div>
</template>

<script lang="ts">
import vue from "vue";

export default vue.extend({
  props: {
    type: String,
    services: Array,
  },
  data() {
    return {
      serviceTypeStatus: false,
      currentSelectedService: {
        id: -1,
        name: "",
        actionParamName: "",
      },
      currentSelectedArea: {
        id: -1,
        name: "",
        actionParamName: "",
      },
    };
  },
  mounted() {},
  methods: {
    getChosenServiceType(serviceName: string) {
      this.serviceName = this.services.filter(
        (service) => service.name == serviceName
      )[0].name;
    },
    saveServiceInfos(id: number) {
      if (this.type == "actions") {
        this.$emit("actionServiceId", id);
      } else {
        this.$emit("reactionServiceId", id);
      }
    },
    saveAreaInfos(id: number) {
      if (this.type == "actions") {
        this.$emit("actionId", id);
      } else {
        this.$emit("reactionId", id);
      }
    },
    saveInputInfos(input: string) {
      if (this.type == "actions") {
        this.$emit("actionParam", input);
      } else {
        this.$emit("reactionParam", input);
      }
    },
  },
});
</script>
