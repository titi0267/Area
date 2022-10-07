<template>
  <div id="createArea">
    <h1 class="logo">Create Area</h1>
    <div class="hl"></div>
    <div class="action">
      <h2 class="actionText">Action</h2>
      <b-button>{{ service[idAction].text }}</b-button>
      <div>
        <b-button style="is-primary" @click="getChosenServiceActionById(1)"
          >Twitter</b-button
        >
        <b-button style="is-primary" @click="getChosenServiceActionById(2)"
          >Youtube</b-button
        >
        <b-button style="is-primary" @click="getChosenServiceActionById(3)"
          >Github</b-button
        >
        <b-button style="is-primary" @click="getChosenServiceActionById(4)"
          >Discord</b-button
        >
        <b-button style="is-primary" @click="getChosenServiceActionById(5)"
          >Trello</b-button
        >
        <b-button style="is-primary" @click="getChosenServiceActionById(6)"
          >Spotify</b-button
        >
      </div>
    </div>
    <div class="vl"></div>
    <div class="reaction">
      <h2 class="reactionText">Reaction</h2>
      <b-button>{{ service[idReaction].text }}</b-button>
      <div>
        <b-button style="is-primary" @click="getChosenServiceReactionById(1)"
          >Twitter</b-button
        >
        <b-button style="is-primary" @click="getChosenServiceReactionById(2)"
          >Youtube</b-button
        >
        <b-button style="is-primary" @click="getChosenServiceReactionById(3)"
          >Github</b-button
        >
        <b-button style="is-primary" @click="getChosenServiceReactionById(4)"
          >Discord</b-button
        >
        <b-button style="is-primary" @click="getChosenServiceReactionById(5)"
          >Trello</b-button
        >
        <b-button style="is-primary" @click="getChosenServiceReactionById(6)"
          >Spotify</b-button
        >
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { text } from "@fortawesome/fontawesome-svg-core";
import vue from "vue";

export default vue.extend({
  data() {
    return {
      service: [
        { text: "Choose your service", id: 0 },
        { text: "Connect to Twitter", id: 1 },
        { text: "Connect to Youtube", id: 2 },
        { text: "Connect to Github", id: 3 },
        { text: "Connect to Discord", id: 4 },
        { text: "Connect to Trello", id: 5 },
        { text: "Connect to Spotify", id: 6 },
      ],
      button: {
        text: "Hide",
      },
      idAction: 0,
      idReaction: 0,
    };
  },
  components: {},
  methods: {
    async getChosenServiceActionById(serviceId: number) {
      this.idAction = serviceId;
    },
    async getChosenServiceReactionById(serviceId: number) {
      this.idReaction = serviceId;
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
