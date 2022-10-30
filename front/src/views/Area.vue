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
    </div>
</template>

<script lang="ts">
import vue from 'vue';
import { Area, Service } from '../types/index'
import SelectArea from '../components/SelectArea.vue'
import SelectService from '../components/SelectService.vue'
import Overview from '../components/Overview.vue'

export default vue.extend({
    data() {
        return {
            services: [] as Service[], /** An array that will be filled with the about.json from the server. */
            area: { /** The data that is used in the area creation. */
                state: 0, /** The status of the area creation. (.../4) */
                actionServiceId: -1, /** The action service ID of the selected service. */
                actionId: -1, /** The ID of the selected action. */
                actionParam: "", /** The parameter linked to the selected action */
                reactionServiceId: -1, /** The reaction service ID of the selected service. */
                reactionId: -1, /** The ID of the selected reaction. */
                reactionParam: "", /** The parameter linked to the selected reaction */
            } as Area
        }
    },
    mounted() {
        this.getAbout();
        this.getLocalStorage();
    },
    components: {
        SelectService, /** Component used for the services selection */
        SelectArea, /** Component used for the action - reaction selection */
        Overview, /** Component used for view the current action -reaction creation */
    },
    methods: {
        /**
         * It gets the area from the localStorage.
         * @data {Object} area
         */
        getLocalStorage(): void {
            let area = JSON.parse(localStorage.getItem('area'));
            if (area != null)
                this.area = area;
        },
        /**
         * It saves the area creation in localStorage.
         * @data {Object} area
         */
        saveAreaLocalStorage(): void {
            localStorage.setItem('area', JSON.stringify(this.area))
        },
        /**
         * A function that sends the area creation to the server.
         * @data {Object} area
         * @async
         */
        async sendServices(): Promise<void> {
            try {
                await this.$axios.post("/areas", {
                    actionServiceId: this.area.actionServiceId,
                    actionId: this.area.actionId,
                    actionParam: this.area.actionParam,
                    reactionServiceId: this.area.reactionServiceId,
                    reactionId: this.area.reactionId,
                    reactionParam: this.area.reactionParam,
                });
                this.$router.push('/home')
                this.toast('Your actions - reaction has been created', 'is-success');
            } catch (err) {
                this.toast(err.response.data.message, 'is-danger');
            }
        },
        /**
         * A function that gets the about.json from the server.
         * @data {Array} services
         * @async
         */
        async getAbout(): Promise<void> {
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

<style scoped lang="scss">
#Area {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-top: 95px;
    height: 100%;
}
</style>
