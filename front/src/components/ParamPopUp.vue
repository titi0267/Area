<template>
    <div id="ParamPopUp">
        <div class="background"/>
        <div class="popUp">
            <p>Please put your {{getParamName().toLowerCase()}}</p>
            <div class="params">
                <b-input :placeholder="getParamName()" v-model="area[type + 'Param']" @input="$emit('save')"></b-input>
                <b-dropdown aria-role="list" v-if="type == 'reaction' && getInjectParams().length != 0">
                    <template #trigger="{ active }">
                        <b-button
                            label="Injected params"
                            type="is-primary"
                            :icon-right="active ? 'chevron-up' : 'chevron-down'" />
                    </template>
                    <b-dropdown-item v-for="param of getInjectParams()" :key="param" @click="appendInput(param)" aria-role="listitem"> {{ param }} </b-dropdown-item>
                </b-dropdown>
            </div>
            <div class="buttons">
                <b-button type="is-danger is-light" @click="$emit('close')">Cancel</b-button>
                <b-button type="is-success is-light" @click="$emit('next')">Done</b-button>
            </div>
        </div>
    </div>
</template>

<script scoped lang="ts">
import vue from 'vue';

export default vue.extend({
    mounted() {
        if (this.getParamName() === '') {
            this.$emit('next')
            this.$emit('save')
        }
    },
    props: {
        services: Array,
        area: Object,
        type: String,
    },
    methods: {
        appendInput(value: string) {
            this.area[this.type + 'Param'] = this.area[this.type + 'Param'] + '%' + value + '%';
        },
        /**
         * It's a function that returns the name
         * @data {Object} area
         * @data {Array} services
         */
        getInjectParams() {
            let service = this.services.find(service => service.id === this.area['actionServiceId']);
            let area: Object;
            if (service != undefined)
                area = service['actions'].find(actrea => actrea.id === this.area['actionId']).availableInjectParams;
            return area;
        },
        /**
         * It's a function that returns the name of the parameter of the action or reaction.
         * @data {Object} area
         * @data {Array} services
         * @data {String} type
         * @return {String} - Return the actual selected service name.
         */
        getParamName(): String {
            if (this.area[this.type + "Id"] == -1 || this.services[0] === null) return;
            let service = this.services.find(
                (service) => service.id == this.area[this.type + "ServiceId"]
            );
            let paramName = service[this.type + "s"].find(
                (actrea) => actrea.id === this.area[this.type + "Id"]
            )[this.type + "ParamName"];
            return paramName;
        },
    }
})
</script>

<style lang="scss" scoped>
#ParamPopUp {
    top: 0px;
    left: 0px;
    position: absolute;
    height: 100%;
    width: 100%;
    z-index: 10;
    p {
        font-family: Hitmo Regular;
    }
    .background {
        opacity: 50%;
        width: 100%;
        height: 100%;
        background-color: black;
        position: absolute;
    }
    .popUp {
        transform: translate(-50%, -50%);
        top: 50%;
        left: 50%;
        position: absolute;
        background-color: white;
        border-radius: 20px;
        padding: 18px;
        width: 560px;
        height: 200px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        .params {
            display: flex;
            flex-direction: column;
            align-items: center;
            >:deep(div) {
                width: 85%;
                &.dropdown {
                    margin-top: 10px;
                }
                .dropdown-menu {
                    width: 100%;
                    margin-top: 10px;
                    top: 35px !important;
                }
                .dropdown-trigger {
                    width: 100%;
                    button {
                        width: 100%;
                        span {
                            margin-left: 10px;
                            svg {
                                height: 20px;
                            }
                        }
                    }
                }
            }
        }
        .buttons {
            display: flex;
            justify-content: space-between;
            margin-top: 10px;
        }
    }
}
</style>
