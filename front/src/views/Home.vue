<template>
    <div id="home">
        <h3>Home</h3>
        <router-link to="/create-area">
            <b-button style="is-primary">Create</b-button>
        </router-link>
        <div v-for="area in areas" :key="area.actionParam" class="box">
            <div class="action">
                <p>{{ services.find(service => service.id == area.actionServiceId).name }}</p>
                <!-- <p>{{ (services.find(service => service.id == area.actionId)).find(action => action.id == area.actionId) }}</p> -->
            </div>
            <div class="reaction">
                <p>{{ services.find(service => service.id == area.reactionServiceId).name }}</p>
                <!-- <p>{{ (services.find(service => service.id == area.reactionId)).find(reaction => reaction.id == area.reactionId) }}</p> -->
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import vue from 'vue';

export default vue.extend({
    data() {
        return {
            areas: [],
            services: [],
        }
    },
    mounted() {
        this.getUserAreas();
        this.getServices();
    },
    components: {

    },
    methods: {
        async getUserAreas() {
            try {
                let { data: resp } = await this.$axios.get('/users/areas')
                this.areas = resp;
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
.box {
    width: 100%;
    height: 30px;
    border: 1px solid black;
}
</style>
