<template>
    <div id="home">
        <h3>Home</h3>
        <router-link to="/create">
            <b-button style="is-primary">Create</b-button>
        </router-link>
        <div v-for="area in areas" :key="area.actionParam" class="box">
            <div class="action" v-if="getActionObject(area)">
                <p> {{ getActionObject(area).name }} </p>
                <p> {{ getActionObject(area).actions.find(action => action.id == area.actionId).name }} </p>
            </div>
            <div class="reaction" v-if="getReactionObject(area)">
                <p> {{ getReactionObject(area).name }} </p>
                <p> {{ getReactionObject(area).reactions.find(reaction => reaction.id == area.reactionId).name }} </p>
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
        getActionObject(area) {
            let result = this.services.find(service => service.id == area.actionServiceId)
            return result;
        },
        getReactionObject(area) {
            let result = this.services.find(service => service.id == area.reactionServiceId)
            return result;
        },
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
    height: 50px;
    border: 1px solid black;
    padding: 0px !important;
}
</style>
