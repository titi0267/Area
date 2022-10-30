<template>
  <div id="SelectArea">
    <h2>Select your {{ type }} type</h2>
    <div>
      <div v-for="service in services" :key="service.name">
        <div class="areas" v-if="service.id == area[type + 'ServiceId']">
          <div class="area"
            :class="{ selected: actrea.id == area[type + 'Id'] }"
            v-for="actrea in service[type + 's']"
            :key="actrea.name"
            @click="$emit(type + 'Id', actrea.id), $emit('save')">
                <p> {{ actrea.name }} </p>
          </div>
        </div>
      </div>
    </div>
    <div class="buttons">
        <b-button @click="$emit('previous'), $emit('save')">
            Previous
        </b-button>
        <b-input class="param-input" @input="$emit(type + 'Param', $event)" :placeholder="getParamName()"></b-input>
        <b-button @click="$emit('next'), $emit('save'), $router.push(type == 'action' ? 'reaction' : 'overview')">
            Next
        </b-button>
    </div>
  </div>
</template>

<script lang="ts">
import vue from "vue";

export default vue.extend({
  data() {
    return {};
  },
  mounted() {},
  props: {
    type: String,
    services: Array,
    area: Object,
  },
  watch: {
    services: function () {
      this.$nextTick(() => {
        this.postOauthCode();
      });
    },
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

        if (code == null) return;
        let serviceName = this.services.find(
          (service) => service.id == this.area[this.type + "ServiceId"]
        ).name;
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
#SelectArea {
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
        .param-input {
            width: 300px;
        }
    }
    .areas {
        margin: 10px;
        margin: 10px;
        display: flex;
        height: 100%;
        position: relative;
        justify-content: center;
        .area {
            height: 150px;
            width: 150px;
            border-radius: 10px;
            margin: 5px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            cursor: pointer;
            padding: 5px;
            border: 1px solid black;
            p {
                font-size: 20px;
                font-family: Hitmo Regular;
                text-transform: uppercase;
                white-space: normal;
            }
            &.selected:not(p) {
                outline: 2px solid black;
            }
        }
    }
}
</style>
