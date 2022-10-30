<template>
  <div id="SelectService">
    <h2>Select your {{ type }} service name</h2>
    <div>
      <b-input class="search-input" @input="debounceInput" placeholder="Enter your service here"></b-input>
      <div class="services">
        <div v-for="service in services" :key="service.name">
          <div class="service"
            :style="{ 'background-color': service.backgroundColor }"
            :class="{ selected: service.id == area[type + 'ServiceId'] }"
            v-if="service[type + 's'].length != 0 && service.name.toLowerCase().includes(filterInput.toLowerCase())"
            @click="$emit(type + 'ServiceId', service.id), $emit('save'), getOAuthUrl()">
                <b-image :src="service.imageUrl"></b-image>
                <p> {{ service.name }} </p>
          </div>
        </div>
      </div>
    </div>
    <div class="buttons">
        <b-button @click="$emit('previous'), $emit('save'), $router.push(area.state == -1 ? '/home' : '/create/action')">
          Previous
        </b-button>
        <b-button @click="$emit('next'), $emit('save'), $emit('loading'), redirectOAuth()" :disabled="oauthURL == ''">
          Next
        </b-button>
    </div>
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
  watch: {
    services: function () {
        this.$nextTick(() => this.getOAuthUrl());
    },
  },
  methods: {
    debounceInput: _.debounce(function (input) {
      this.filterInput = input;
    }, 400),
    async getOAuthUrl(): Promise<any> {
      try {
        let serviceName = this.services.find(service => service.id == this.area[this.type + "ServiceId"]).name;
        const { data: url } = await this.$axios.get(
          "/oauth/" + (serviceName == "Youtube" ? "google" : serviceName.toLowerCase()) +
            "/link/front"
        );
        this.oauthURL = url;
      } catch (err) {
        this.oauthURL = '';
      }
    },
    redirectOAuth(): void {
      window.location.href = this.oauthURL;
    }
  },
});
</script>

<style scoped lang="scss">
#SelectService {
    padding: 20px;
    padding-top: 0px;
    width: 100%;
    height: 100%;
    position: relative;
    .search-input {
        display: flex;
        justify-content: center;
        :deep(input) {
            width: 400px;
            margin: 10px;
        }
    }
    h2 {
        font-family: 'Courier New', Courier, monospace;
    }
    .buttons {
        display: flex;
        position: absolute;
        padding: 0px 30px;
        left: 0;
        width: 100%;
        bottom: 20px;
        justify-content: space-between;
        :deep(button) {
            width: 100px;
            span, a {
                color: hsl(0deg, 0%, 21%);
            }
        }
    }
    .services {
        margin: 10px;
        margin: 10px;
        display: flex;
        height: 100%;
        position: relative;
        justify-content: center;
        .service {
            height: 150px;
            width: 150px;
            border-radius: 10px;
            margin: 5px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            cursor: pointer;
            padding: 5px;
            p {
                font-size: 20px;
                font-family: Hitmo Regular;
                text-transform: uppercase;
            }
            &.selected:not(p) {
                outline: 2px solid black;
            }
        }
    }
}
</style>
