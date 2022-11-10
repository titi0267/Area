<template>
    <div id="app">
        <NavBar v-if="$route.name !== 'register' && $route.name !== 'login' && navBarPageNotFound() === true"/>
        <router-view/>
    </div>
</template>

<script scoped lang="ts">
import vue from 'vue';
import NavBar from './components/NavBar.vue'

export default vue.extend({
    components: {
        NavBar, /** Navigation bar */
    },
    methods: {
        /**
         * Checking if the user is on the not-found page and if the user is not logged in. If the user is not logged in, then the navbar will not be shown.
         */
        navBarPageNotFound(): boolean {
            if (this.$route.name === 'not-found' && this.$store.getters.userToken.length === 0)
                return false;
            return true;
        }
    }
})
</script>

<style scoped lang="scss">
#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    height: 100vh;
}

/** ALL FONTS ARE LOADED HERE */

@font-face {
    font-family: "Hitmo Regular";
    src: url("@/assets/fonts/HITMO-REGULAR.ttf");
}
@font-face {
    font-family: "Bebas Regular";
    src: url("@/assets/fonts/BEBASNEUE-REGULAR.ttf");
}
@font-face {
    font-family: "Avenir Roman";
    src: url("@/assets/fonts/AVENIRLTSTD-ROMAN.OTF");
}
@font-face {
    font-family: "Century Gothic Regular";
    src: url("@/assets/fonts/CENTURYGOTHIC-REGULAR.TTF");
}
</style>
