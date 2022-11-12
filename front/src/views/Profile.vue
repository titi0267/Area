<template>
    <div id="Profile">
        <div class="about-you">
            <h2>About You</h2>
            <p>First Name: {{ user.firstName }}</p>
            <p>Last Name: {{ user.lastName }}</p>
            <p>E-mail: {{ user.email }}</p>
        </div>
    </div>
</template>

<script scoped lang="ts">
import vue from 'vue';
import { User } from '../types/index'

export default vue.extend({
    data() {
        return {
            user: [] as User[], /** An array that is fill with the user infos (email, first name, last name...) */
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
    padding: 20px;
    padding-top: 110px;
    .about-you {
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        background-color: white;
        box-shadow: 0 0 15px 1px rgb(0 0 0 / 28%);
        border-radius: 20px;
        width: 60%;
        h2 {
            padding: 8px 11px;
            font-size: 20px;
            padding-bottom: 2px;
            font-family: Avenir Roman;
            width: 100%;
            text-align: start;
            border-bottom: 1px solid rgba(102, 102, 102, 0.712);
        }
        p {
            width: 100%;
            padding: 7px 15px;
            text-align: start;
            font-family: Century Gothic Regular;
        }
    }
}
</style>
