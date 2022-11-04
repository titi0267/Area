<template>
    <div id="Create">
        <b-loading :is-full-page="true" v-model="loading"/>
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
            if (this.area == null) {
                this.notification('An error occured', 'is-danger')
                this.$router.push('/home')
            } if (this.area.state <= 1) {
                this.$router.push('create/action')
            } else if (this.area.state <= 3) {
                this.$router.push('create/reaction')
            }
        }
    }
})
</script>

<style scoped lang="scss">

</style>