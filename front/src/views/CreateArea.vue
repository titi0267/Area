<template>
  <div id="createArea">
    <h1 class="logo">Create Area</h1>
    <div class="hl"></div>
    <div class="action">
      <h2 class="actionText">Action</h2>
      <SelectServices
        type="actions"
        :services="services"
        @actionParam="actionParam = $event"
        @actionId="actionId = $event"
        @actionServiceId="actionServiceId = $event"
      />
    </div>
    <div class="vl"></div>
    <div class="reaction">
      <h2 class="reactionText">Reaction</h2>
      <SelectServices
        type="reactions"
        :services="services"
        @reactionParam="reactionParam = $event"
        @reactionId="reactionId = $event"
        @reactionServiceId="reactionServiceId = $event"
      />
    </div>
    <div style="position: absolute; top: 0px">
      <b-button @click="sendServices()">Create AREA</b-button>
    </div>
  </div>
</template>

<script lang="ts">
import vue from "vue";
import SelectServices from "../component/SelectServices.vue";

export default vue.extend({
  data() {
    return {
      services: [] as {
        id: number;
        name: string;
        actions: {
          id: number;
          name: string;
          actionParamName: string;
          description: string;
        }[];
        reactions: {
          id: number;
          name: string;
          reactionParamName: string;
          description: string;
        }[];
      }[],
      actionServiceId: -1,
      actionId: -1,
      actionParam: "",
      reactionServiceId: -1,
      reactionId: -1,
      reactionParam: "",
    };
  },
  mounted() {
    this.getServices();
  },
  components: {
    SelectServices,
  },
  methods: {
    async sendServices() {
      try {
        await this.$axios.post("/areas", {
          actionServiceId: this.actionServiceId,
          actionId: this.actionId,
          actionParam: this.actionParam,
          reactionServiceId: this.reactionServiceId,
          reactionId: this.reactionId,
          reactionParam: this.reactionParam,
        });
      } catch (err) {
        console.log(err);
      }
    },
    async getServices() {
      try {
        let { data: services } = await this.$axios.get("/about.json");
        this.services = services;
      } catch (err) {
        console.log(err);
      }
    },
  },
});
</script>

<style lang="scss">
.reactionText {
  font-size: 4vh;
}
.reaction {
  display: flex;
  float: right;
  justify-content: center;
  width: 50%;
  flex-direction: column;
}
.actionText {
  font-size: 4vh;
}
.logo {
  font-size: 5vh;
}
.vl {
  display: flex;
  border-left: 6px solid black;
  height: 100%;
  position: absolute;
  left: 50%;
  margin-left: -3px;
}
.hl {
  display: flex;
  border-top: 6px solid black;
  width: 100%;
}
.action {
  display: flex;
  width: 50%;
  float: left;
  justify-content: center;
  flex-direction: column;
}
</style>
