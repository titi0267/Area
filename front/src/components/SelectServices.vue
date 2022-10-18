<template>
  <div>
    <div>
      <b-button>
        <a v-bind:href="getGoogleOAuthURL()">{{
          currentSelectedService.name.length == 0
            ? "Choose a service"
            : "Connect to " + currentSelectedService.name
        }}</a>
      </b-button>
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
    url: String,
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
    getGoogleOAuthURL() {
      const rootUrl = "https://accounts.google.com/o/oauth2/v2/auth";

      const options = {
        redirect_uri: process.env.VUE_APP_GOOGLE_OAUTH_REDIRECT_URL as string,
        client_id: process.env.VUE_APP_GOOGLE_CLIENT_ID as string,
        access_type: "offline",
        response_type: "code",
        prompt: "consent",
        scope: [
          "https://www.googleapis.com/auth/userinfo.profile",
          "https://www.googleapis.com/auth/userinfo.email",
          "https://www.googleapis.com/auth/youtube.readonly",
          "https://www.googleapis.com/auth/youtube",
          "https://www.googleapis.com/auth/youtube.upload",
        ].join(" "),
      };

      const qs = new URLSearchParams(options);

      return `${rootUrl}?${qs.toString()}`;
    },
  },
});
</script>
