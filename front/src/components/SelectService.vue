<template>
    <div id="SelectService">
        <h2>Sélectionnez le service de votre {{ type }} </h2>
        <div>
            <b-input @input="debounceInput"></b-input>
            <div class="services">
                <div v-for="service in services" :key="service.name">
                    <div class="service" v-if="service[type + 's'].length != 0 && service.name.toLowerCase().includes(filterInput.toLowerCase())" @click="$emit(type + 'ServiceId', service.id)">
                        {{ service.name }}
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import vue from 'vue';
import _ from 'lodash';

export default vue.extend({
    data() {
        return {
            filterInput: "",
        }
    },
    mounted() {
        },
    props: {
        type: String,
        services: Array,
    },
    components: {
    },
    methods: {
        debounceInput: _.debounce(function(input) {
            this.filterInput = input;
        }, 400)
    }
})
</script>

<style lang="scss">
.services {
    display: flex;
}

.service {
    border: 1px solid black;
    height: 150px;
    width: 150px;
    border-radius: 10px;
    cursor: pointer;
}
</style>
