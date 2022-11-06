<template>
    <div id="Create">
        <b-loading :is-full-page="true" :active="true"/>
    </div>
</template>

<script lang="ts">
import vue from 'vue';
import { Area } from '../types/index'

export default vue.extend({
    data() {
        return {
            area: {} as Area,
            loading: true,
        }
    },
    mounted() {
        this.getLocalStorage();
        this.redirect();
    },
    methods: {
        getLocalStorage(): void {
            let area: Area = JSON.parse(localStorage.getItem('area'));
            if (area != null)
                this.area = area;
        },
        redirect(): void {
            if (JSON.stringify(this.area)== '{}') {
                this.$router.push({path: 'services', query: this.$route.query})
            } if (this.area.state <= 1) {
                this.$router.push({path: 'create/action', query: this.$route.query})
            } else if (this.area.state <= 3) {
                this.$router.push({path: 'create/reaction', query: this.$route.query})
            }
        }
    }
})
</script>

<style scoped lang="scss">

</style>