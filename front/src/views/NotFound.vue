<template>
    <div id="notFound" :class="{ 'darkMode' : $store.getters.darkMode }">
        <h1>Error 404</h1>
        <h2>The current page does not exist</h2>
        <b-button type="is-dark" @click="$router.push({name: redirect()})">Go to the {{ redirect() }} page</b-button>
    </div>
</template>

<script scoped lang="ts">
import vue from 'vue';

export default vue.extend({
    methods: {
        /**
         * A method that returns login or home if the user is logged or not.
         */
        redirect(): string {
            if (this.$store.getters.userToken.length === 0)
                return 'login'
            return 'home'
        }
    }
})
</script>

<style scoped lang="scss">
#notFound {
    padding-left: 25%;
    text-align: start;
    padding-top: 130px;
    font-family: "Avenir Roman";
    &.darkMode {
        h1,
        h2 {
            color: white;
        }
        :deep(button) {
            background-color: white;
            color: black;
            &:hover {
                background-color: rgb(180, 180, 180);
            }
        }
    }
    h1 {
        font-size: 25px;
        font-family: Hitmo Regular;
    }
    h2 {
        font-size: 20px;
    }
    :deep(button) {
        margin-top: 20px;
    }
}
</style>
