<template>
    <div id="Home">
        <div v-for="area in areas" :key="area.actionServiceId.toString() + area.actionParam + area.reactionId.toString() + area.actionId.toString()" class="area-list">
            <div class="action" v-if="getActionObject(area)">
                <b-image :src="getActionObject(area).imageUrl"></b-image>
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
            let { data: resp } = await this.$axios.get('/users/areas')
            this.areas = resp;
        },
        async getServices() {
            let { data: services } = await this.$axios.get("/about.json");
            this.services = services.server.services;
        },
    }
})
</script>

<style scoped lang="scss">
#Home {
    padding: 20px;
    .area-list {
        
    }
}
.area-list {
    border: 1px solid black;
    display: flex;
    justify-content: space-around;
}
</style>
