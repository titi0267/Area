<template>
    <div id="app">
        <NavBar v-if="$route.name != 'register' && $route.name != 'login'"/>
        <router-view/>
    </div>
</template>

<script scoped lang="ts">
import vue from 'vue';
import NavBar from './components/NavBar.vue'

export default vue.extend({
    components: {
        NavBar,
    },
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
    src: url("@/assets/fonts/AVENIRLTSTD-ROMAN.ttf");
}
</style>
