<template>
    <div id="Home">
        <div class="no-area" v-if="!areas.length">
            <p>You currently have no action - reaction</p>
        </div>
        <div v-else class="area-list" v-for="area in areas" :key="area.actionServiceId.toString() + area.actionParam + area.reactionId.toString() + area.actionId.toString()"
            :style="{ 'background' : `linear-gradient(to right, ${getActionObject(area) ? getActionObject(area).backgroundColor : ''}, ${getReactionObject(area) ? getReactionObject(area).backgroundColor : ''})` }">
            <div class="action" v-if="getActionObject(area)">
                <b-image :src="getActionObject(area).imageUrl"></b-image>
                <p> {{ getActionObject(area).name }} </p>
                <p> {{ getActionObject(area).actions.find(action => action.id == area.actionId).name }} </p>
            </div>
            <div class="edit">
                <p>Edit</p>
            </div>
            <div class="reaction" v-if="getReactionObject(area)">
                <p> {{ getReactionObject(area).reactions.find(reaction => reaction.id == area.reactionId).name }} </p>
                <p> {{ getReactionObject(area).name }} </p>
                <b-image :src="getReactionObject(area).imageUrl"></b-image>
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
    padding-top: 95px;
    font-family: 'Courier New', Courier, monospace;
    .no-area {
        margin-top: 15px;
    }
    .area-list {
        border-radius: 10px;
        height: 60px;
        display: flex;
        justify-content: space-around;
        margin-bottom: 20px;
        .action,
        .reaction {
            width: 50%;
            display: flex;
            align-items: center;
            :deep(figure) {
                margin: 10px;
                max-height: 50px;
                height: 50px;
                width: auto;
            }
            p {
                color: white;
                margin: 15px;
            }
        }
        .reaction {
            justify-content: flex-end;
        }
        .edit {
            display: flex;
            align-items: center;
            text-transform: uppercase;
            color: white;
            p {
                padding: 5px;
                background-color: rgb(44, 44, 53);
                cursor: pointer;
            }
        }
    }
}
</style>
