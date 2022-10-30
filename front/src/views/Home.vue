<template>
    <div id="home">
        <h3>Home</h3>
        <router-link to="/create/action">
            <b-button style="is-primary" @click="removeLocalStorageItem">Create</b-button>
        </router-link>
        <div v-for="area in areas" :key="area.actionServiceId.toString() + area.actionParam + area.reactionId.toString() + area.actionId.toString()" class="area-list">
            <div class="action" v-if="getService(area, 'action')">
                <p> {{ getService(area, 'action').name }} </p>
                <p> {{ getService(area, 'action').actions.find(action => action.id == area.actionId).name }} </p>
            </div>
            <div class="reaction" v-if="getService(area, 'reaction')">
                <p> {{ getService(area, 'reaction').name }} </p>
                <p> {{ getService(area, 'reaction').reactions.find(reaction => reaction.id == area.reactionId).name }} </p>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import vue from 'vue';
import { Areas, Service } from '../types/index'

export default vue.extend({
    data() {
        return {
            areas: [] as Areas[], /** An array that will be filled with the actions and reactions of the users */
            services: [] as Service[], /** An array that will be filled with the about.json from the server. */
        }
    },
    mounted() {
        this.getUserAreas();
        this.getAbout();
    },
    methods: {
        /**
         * It removes the localStorage item called 'area'
         */
        removeLocalStorageItem(): void {
            localStorage.removeItem('area');
        },
        /**
         * A method that returns a service object.
         * @param {Object} area - Area creation
         * @param {String} type - Action or Reaction
         * @data {Array} services
         */
        getService(area, type): Service {
            let serviceObject = this.services.find(service => service.id == area[type + "ServiceId"])
            return serviceObject;
        },
        /**
         * A function that gets the actions and reactions of the user from the server.
         * @data {Array} areas
         * @async
         */
        async getUserAreas(): Promise<void> {
            try {
                let { data: resp } = await this.$axios.get('/users/areas')
                this.areas = resp;
            } catch (err) {
                console.log(err);
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
.area-list {
    border: 1px solid black;
    display: flex;
    justify-content: space-around;
}
</style>
