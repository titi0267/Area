<template>
  <div id="SelectService">
    <h2>Select your {{ type }} service name</h2>
    <div>
      <b-input class="search-input" @input="filterInput = $event" placeholder="Search your service here"></b-input>
      <div class="services">
        <div v-for="service in services" :key="service.name">
          <div
            class="service"
            :style="{ 'background-color': service.backgroundColor }"
            :class="{ selected: service.id == area[type + 'ServiceId'] }"
            v-if="service[type + 's'].length != 0 && service.name.toLowerCase().includes(filterInput.toLowerCase())"
            @click="$emit(type + 'ServiceId', service.id), $emit('next'), $emit('save'), getOAuthUrl()">
                <p> {{ service.name }} </p>
                <b-image :src="$store.state.serveurURL + service.imageUrl"></b-image>
          </div>
        </div>
      </div>
    </div>
    <div class="buttons">
      <b-button
        @click="
          $emit('previous'),
            $emit('save'),
            $router.push(area.state == -1 ? '/home' : '/create/action')"
      >
        Previous
      </b-button>
      <b-button @click="$emit('save')">
        Next
      </b-button>
    </div>
  </div>
</template>

<script scoped lang="ts">
import vue from "vue";

export default vue.extend({
  data() {
    return {
      filterInput: "" /** It's a filter input used for search a service */,
      oauthURL: "" /** This variable contains the oAuth URL of the right service */,
      picture: "",
    };
  },
  props: {
    type: String /** Type between 'action' or 'reaction' */,
    services: Array /** Array that contains the About.JSON file */,
    area: Object /** Object that contains the area creation fields */,
    tokensTable: Object, /** Object that contains all oauth tokens of the user */
  },
  methods: {
    /**
     * An async function that gets the oauth url for the service selected.
     * @data {Object} area
     * @data {Array} services
     * @data {Object} tokensTable
     * @data {String} type
     * @data {String} oauthURL
     * @async
     */
    async getOAuthUrl(): Promise<void> {
      try {
        let serviceOauthName = this.services.find(
          (service) => service.id == this.area[this.type + "ServiceId"]
        )['oauthName'];
        if (serviceOauthName == null) {
          this.notification('A problem occured, please select another ' + this.type, 'is-danger');
          this.$emit('previous');
          this.$emit('save');
          return;
        }
        this.$emit('loading');
        const { data: url } = await this.$axios.get("/oauth/" + serviceOauthName + "/link", {
          headers: {
            Authorization: this.$store.getters.userToken || "noToken",
            },
        });
        this.oauthURL = url;
        this.redirectOAuth();
      } catch {
        this.oauthURL = "";
      }
    },
    /**
     * It's a function that redirects the user to the oAuth URL of the service selected.
     * @data {String} type
     * @data {Array} services
     * @data {String} type
     * @data {Object} tokensTable
     * @data {String} oauthURL
     */
    redirectOAuth(): void {
        let serviceOauthName = this.services.find(
            (service) => service.id == this.area[this.type + "ServiceId"]
        )['oauthName'];
        if (this.tokensTable[serviceOauthName + 'Token'] == null) {
            window.location.href = this.oauthURL;
        }
    },
  },
});
</script>

<style scoped lang="scss">
$service-size: 175px;

#SelectService {
    padding: 20px;
    padding-top: 0px;
    width: 100%;
    height: 100%;
    position: relative;
    .search-input {
        display: flex;
        justify-content: center;
        margin-bottom: 10px;
        :deep(input) {
            width: 400px;
            margin: 10px;
        }
    }
    h2 {
        margin: 20px 0px 10px;
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
        flex-wrap: wrap;
        .service {
            height: $service-size;
            width: $service-size;
            border-radius: 10px;
            margin: 10px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            cursor: pointer;
            padding: 15px;
            p {
                font-size: 20px;
                font-family: Hitmo Regular;
                text-transform: uppercase;
                color: white;
            }
            :deep(figure) {
                height: 85px;
                width: auto;
                margin: auto;
                margin-top: 20px;
                img {
                    height: 85px;
                    width: auto;
                }
            }
            &.selected:not(p) {
                outline: 2px solid black;
            }
        }
    }
}
</style>
