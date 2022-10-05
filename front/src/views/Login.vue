<template>
    <div id="login">
        <h3>Login</h3>
        <b-field label="E-mail" label-position="on-border">
            <b-input v-model="email"></b-input>
        </b-field>
        <b-field label="Password" label-position="on-border">
            <b-input v-model="password"></b-input>
        </b-field>
        <b-button type="is-primary">Login</b-button>
        <br/>
        <router-link to="/register">
            <a>Not account ? Register</a>
        </router-link>
    </div>
</template>

<script lang="ts">
import vue from 'vue';

export default vue.extend ({
    data() {
        return {
            email: "",
            password: "",
        }
    },
    components: {

    },
    methods: {
        async sendLogin() {
            try {
                let {data: resp} = await this.$axios.post('----', {
                    headers: {
                        Authorization: this.$store.getters.userToken,
                    },
                    'email': this.email,
                    'password': this.password
                })
                localStorage.setItem('usr-token', resp.token);
                this.$store.commit('updateToken', resp.token);
            } catch (err) {
                console.log(err);
            }
        }
    }
})
</script>

<style scoped lang="scss">

</style>
