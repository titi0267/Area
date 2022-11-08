<template>
    <div id="Overview">
        <!-- <b-icon class="previous" icon="chevron-right" @click.native="$emit('previous'), $emit('save'), $router.push('/create/reaction')"></b-icon> -->
        <h2 class="overview-title">Overview of your action - reaction creation</h2>
        <div class="if" v-if="action.actions">
            <h2>IF</h2>
            <div class="action" :style="{ 'background-color' : action.backgroundColor }">
                <p>Action</p>
                <b-image :src="$store.state.serveurURL + action.imageUrl"></b-image>
                <span style="margin: auto;">
                    <h1> {{ action.actions.name }} </h1>
                    <h2> {{ action.actions.description }} </h2>
                </span>
                <span style="width: 85%;">
                    <h3> {{ action.actions.actionParamName }} </h3>
                    <b-input v-if="action.actions.actionParamName" disabled :value="area.actionParam" />
                </span>
            </div>
        </div>
        <div class="then" v-if="reaction.reactions">
            <h2>THEN</h2>
            <div class="reaction" :style="{ 'background-color' : reaction.backgroundColor }">
                <p>Reaction</p>
                <b-image :src="$store.state.serveurURL + reaction.imageUrl"></b-image>
                <span style="margin: auto;">
                    <h1> {{ reaction.reactions.name }} </h1>
                    <h2> {{ reaction.reactions.description }} </h2>
                </span>
                <span style="width: 85%;">
                    <h3> {{ reaction.reactions.reactionParamName }} </h3>
                    <b-input v-if="reaction.reactions.reactionParamName" disabled :value="area.reactionParam" />
                </span>
            </div>
        </div>
        <b-button @click="$emit('create')" type="is-success is-light">Create</b-button>
         <!-- <b-button @click="$emit('previous'), $router.push('/create/reaction')">Précédent</b-button> -->
    </div>
</template>

<script scoped lang="ts">
import vue from 'vue';
import { Action, Reaction } from '../types/index'

export default vue.extend({
    data() {
        return {
            action: {} as Action, /** Current created action */
            reaction: {} as Reaction, /** Current created reaction */
        }
    },
    mounted() {
        this.getArea('action');
        this.getArea('reaction');
    },
    props: {
        area: Object /** Object that contains the area creation fields */,
        services: Array /** Array that contains the About.JSON file */,
    },
    methods: {
        /**
         * It's a method that is called when the component is mounted. It's used to get the action and reaction from the services array.
         * @param {string} type - Type between action or reaction
         * @data {Object} area
         * @data {Array} services
         */
        getArea(type: string): void {
            this.$nextTick(() => {
                let area = this.services.find(service => service.id == this.area[type + 'ServiceId']);
                if (area) {
                    this[type] = area;
                    this[type][type + 's'] = area[type + 's'].find(actrea => actrea.id == this.area[type + 'Id']);
                }
            })
        }
    }
})
</script>

<style lang="scss" scoped>
#Overview {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
    align-items: center;
    .previous {
        position: absolute;
        transform: rotate(180deg);
        top: 120px;
        left: 30px;
        cursor: pointer;
    }
    .overview-title {
        position: absolute;
        top: 120px;
        font-family: 'Courier New', Courier, monospace;
    }
    .if,
    .then {
        font-size: 25px;
        margin-bottom: 150px;
    }
    .action,
    .reaction {
        margin-bottom: 40px;
        border-radius: 20px;
        width: 450px;
        height: 325px;
        padding: 10px;
        color: white;
        flex-direction: column;
        display: flex;
        align-items: center;
        h1 {
            text-transform: uppercase;
            font-family: Hitmo Regular;
        }
        h2 {
            font-family: Hitmo Regular;
            font-size: 18px;
        }
        h3 {
            font-family: "Avenir Roman";
            font-size: 20px;
            text-align: start;
        }
        :deep(figure) {
            img {
                width: auto;
                height: 65px;
                margin: 10px auto;
            }
        }
        p {
            font-size: 20px;
            font-family: 'Courier New', Courier, monospace;
        }
    }
    :deep(button) {
        position: absolute;
        bottom: 150px;
        height: 60px;
        width: 140px;
        border-radius: 10px;
        font-size: 20px;
    }
}
</style>
