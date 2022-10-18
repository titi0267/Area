<template>
    <div id="Area">
        <SelectService v-if="state == 0 || state == 2" :services="services" @actionServiceId="area.actionServiceId = $event, state++" @reactionServiceId="area.reactionServiceId = $event, state++" :type="stateType()"/>
        <SelectArea v-if="state == 1 || state == 3" :services="services" @actionId="area.actionId = $event, state++" @reactionId="area.reactionId = $event, state++" :type="stateType()"/>
        <p> {{ area }} </p>
    </div>
</template>

<script lang="ts">
import vue from 'vue';
import SelectArea from '../components/SelectArea.vue'
import SelectService from '../components/SelectService.vue'

export default vue.extend({
    data() {
        return {
            services: [],
            state: 0,
            area: {
                actionServiceId: -1,
                actionId: -1,
                actionParam: "",
                reactionServiceId: -1,
                reactionId: -1,
                reactionParam: "",
            }
        }
    },
    mounted() {
        this.getServices();
    },
    components: {
        SelectArea,
        SelectService,
    },
    methods: {
        stateType() {
            if (this.state == 0 || this.state == 2)
                return 'action'
            else
                return 'reaction'
        },
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
                this.services = services.server.services;
            } catch (err) {
                console.log(err);
            }
        },
    }
})
</script>

<style lang="scss">
#Area {
    display: flex;
    flex-direction: column;
    align-items: center;
}
</style>
