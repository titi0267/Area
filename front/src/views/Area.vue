<template>
  <div id="Area">
    <SelectService
      v-if="area.state == 0 || area.state == 2"
      :services="services"
      :area="area"
      @actionServiceId="area.actionServiceId = $event"
      @reactionServiceId="area.reactionServiceId = $event"
      @next="area.state++"
      @save="saveAreaLocalStorage"
      @previous="area.state--"
      :type="$route.path.split('/')[2]"
    />
    <SelectArea
      v-else-if="area.state == 1 || area.state == 3"
      :services="services"
      :area="area"
      @actionId="area.actionId = $event"
      @actionParam="area.actionParam = $event"
      @reactionId="area.reactionId = $event"
      @reactionParam="area.reactionParam = $event"
      @next="area.state++"
      @previous="area.state--"
      @save="saveAreaLocalStorage"
      :type="$route.path.split('/')[2]"
    />
    <Overview
      v-else
      @create="sendServices"
      @previous="area.state--"
      :area="area"
    />
    <p>{{ area }}</p>
  </div>
</template>

<script lang="ts">
import vue from "vue";
import SelectArea from "../components/SelectArea.vue";
import SelectService from "../components/SelectService.vue";
import Overview from "../components/Overview.vue";

export default vue.extend({
  data() {
    return {
      area: {
        state: 0,
        actionServiceId: -1,
        actionId: -1,
        actionParam: "",
        reactionServiceId: -1,
        reactionId: -1,
        reactionParam: "",
      },
      services: [] as {
        id: 1;
        serviceName: "";
        imageUrl: "";
        backgroundColor: "";
        oauthName: "";
        actions: {
          id: 1;
          name: "";
          actionParamName: "";
          fct: "";
        }[];
        reactions: {
          id: 1;
          name: "";
          reactionParamName: "";
          fct: "";
        }[];
      }[],
    };
  },
  mounted() {
    this.getServices();
    this.getLocalStorage();
  },
  components: {
    SelectArea,
    SelectService,
    Overview,
  },
  methods: {
    getLocalStorage() {
      let area = JSON.parse(localStorage.getItem("area"));
      if (area != null) this.area = area;
    },
    saveAreaLocalStorage() {
      localStorage.setItem("area", JSON.stringify(this.area));
    },
    async sendServices() {
      try {
        await this.$axios.post("/areas", {
          actionServiceId: this.area.actionServiceId,
          actionId: this.area.actionId,
          actionParam: this.area.actionParam,
          reactionServiceId: this.area.reactionServiceId,
          reactionId: this.area.reactionId,
          reactionParam: this.area.reactionParam,
        });
        this.$router.push("/home");
      } catch (err) {
        console.log(err);
      }
    },
    async getServices() {
      try {
        let { data: services } = await this.$axios.get("/about.json");
        this.services = services.server.services;
      } catch (err) {
        console.log(err);
      }
    },
  },
});
</script>

<style scoped lang="scss">
#Area {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
