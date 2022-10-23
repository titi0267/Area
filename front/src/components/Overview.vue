<template>
    <div id="Overview">
        {{ area }}
        <b-button @click="$emit('previous'), $router.push('/create/reaction')">Précédent</b-button>
        <b-button @click="$emit('create')">Créer</b-button>
    </div>
</template>

<script scoped lang="ts">
import vue from 'vue';

export default vue.extend({
    data() {
        return {
        }
    },
    mounted() {
    },
    props: {
        area: Object,
    },
    components: {
    },
    methods: {
    }
})
</script>

<style lang="scss">
</style>
