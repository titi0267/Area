<template>
    <div id="Home">
        <div class="no-area" v-if="!areas.length">
            <p>You currently have no action - reaction</p>
        </div>
        <div v-else v-for="area in areas" :key="area.actionServiceId.toString() + area.actionParam + area.reactionId.toString() + area.actionId.toString()">
            <div class="area-list" :style="{ 'background' : `linear-gradient(to right, ${getService(area, 'action') ? getService(area, 'action').backgroundColor : ''}, ${getService(area, 'reaction') ? getService(area, 'reaction').backgroundColor : ''})` }">
                <div class="action" v-if="getService(area, 'action')">
                    <b-image :src="$store.state.serveurURL + getService(area, 'action').imageUrl"></b-image>
                    <p> {{ getService(area, 'action').name }} </p>
                    <p> {{ getService(area, 'action').actions.find(action => action.id == area.actionId).name }} </p>
                </div>
                <div class="edit" :class="{'isActive' : isEditing[area.id]}">
                    <b-icon icon="pen" @click.native="setEdit(area.id)"></b-icon>
                </div>
                <div class="reaction" v-if="getService(area, 'reaction')">
                    <p> {{ getService(area, 'reaction').reactions.find(reaction => reaction.id == area.reactionId).name }} </p>
                    <p> {{ getService(area, 'reaction').name }} </p>
                    <b-image :src="$store.state.serveurURL + getService(area, 'reaction').imageUrl"></b-image>
                </div>
            </div>
            <div v-show="isEditing[area.id]">
                <EditArea :area="area" :action="getService(area, 'action')" :reaction="getService(area, 'reaction')" @deleted="deleteArea(area.id)"/>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import vue from 'vue';
import { Areas, Service } from '../types/index'
import EditArea from '../components/EditArea.vue'

export default vue.extend({
    data() {
        return {
            areas: [] as Areas[], /** An array that will be filled with the actions and reactions of the users */
            services: [] as Service[], /** An array that will be filled with the about.json from the server. */
            isEditing: [] as Boolean[]
        }
    },
    mounted() {
        this.getUserAreas();
        this.getAbout();
    },
    components: {
        EditArea
    },
    methods: {
        deleteArea(id: number) : void {
            const areaIndex = this.areas.findIndex(area => area.id == id)
            this.areas.splice(areaIndex, 1);
        },
        setEdit(id: number): void {
            this.isEditing[id] == true ? this.$set(this.isEditing, id, false) : this.$set(this.isEditing, id, true);
        },
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
    padding-top: 110px;
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
        padding: 10px;
        .action,
        .reaction {
            width: 50%;
            display: flex;
            align-items: center;
            :deep(figure) {
                height: 40px;
                width: 40px;
                width: auto;
                margin: 10px;
                max-height: 50px;
                img {
                    height: 100%;
                    width: auto;
                    object-fit: contain;
                }
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
            transition-property: transform;
            transition-duration: 0.5s;
            &.isActive {
                transform: rotate(180deg);
            }
            :deep(span) {
                padding: 20px;
                box-shadow: 0 0 15px 1px #000000a1;
                background-color: white;
                border-radius: 50%;
                cursor: pointer;
                svg {
                    width: 19px;
                    height: 19px;
                }
            }
        }
    }
}
</style>
