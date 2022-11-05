<template>
    <div id="Profile">
        {{ user }}
    </div>
</template>

<script scoped lang="ts">
import vue from 'vue';

export default vue.extend({
    data() {
        return {
            user: [],
        }
    },
    mounted() {
        this.getUserInfos()
    },
    methods: {
        /**
         * That function is used to get the infos of the user.
         * @data {Array} user
         * @async
         */
        async getUserInfos(): Promise<any> {
            let {data: user} = await this.$axios.get('/users/me', {
                headers: {
                    Authorization: this.$store.getters.userToken || "noToken",
                }
            })
            this.user = user
        }
    }
})
</script>

<style lang="scss" scoped>
#Profile {
    padding-top: 95px;
}
</style>
