<template>
    <div id="Home">
        <div class="no-area" v-if="!areas.length">
            <p>You currently have no action - reaction</p>
        </div>
        <div v-else class="area-list" v-for="area in areas" :key="area.actionServiceId.toString() + area.actionParam + area.reactionId.toString() + area.actionId.toString()"
            :style="{ 'background' : `linear-gradient(to right, ${getService(area, 'action') ? getService(area, 'action').backgroundColor : ''}, ${getService(area, 'reaction') ? getService(area, 'reaction').backgroundColor : ''})` }">
            <div class="action" v-if="getService(area, 'action')">
                <b-image :src="getService(area, 'action').imageUrl"></b-image>
                <p> {{ getService(area, 'action').name }} </p>
                <p> {{ getService(area, 'action').actions.find(action => action.id == area.actionId).name }} </p>
            </div>
            <div class="edit">
                <p>Edit</p>
            </div>
            <div class="reaction" v-if="getService(area, 'reaction')">
                <p> {{ getService(area, 'reaction').reactions.find(reaction => reaction.id == area.reactionId).name }} </p>
                <p> {{ getService(area, 'reaction').name }} </p>
                <b-image :src="getService(area, 'reaction').imageUrl"></b-image>
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
                let { data: resp } = await this.$axios.get('/users/areas', {
                    headers: {
                        Authorization: this.$store.getters.userToken || "noToken",
                    }
                })
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
