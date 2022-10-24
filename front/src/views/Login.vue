<template>
    <div id="login">
        <h3>Login</h3>
        <b-field label="E-mail" label-position="on-border">
            <b-input type="email" @input="checkEmail($event)" v-model="email"></b-input>
        </b-field>
        <b-field label="Password" label-position="on-border">
            <b-input type="password" password-reveal v-model="password"></b-input>
        </b-field>
        <b-button @click="sendLogin()" type="is-primary">Login</b-button>
        <br/>
        <router-link to="/register">
            <a>Not account ? Register</a>
        </router-link>
    </div>
</template>

<script lang="ts">
import vue from 'vue';

export default vue.extend({
    data() {
        return {
            email: "",
            password: "",
        }
    },
    components: {

    },
    methods: {
        checkEmail(input) {
            const email_regex = (/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
            // if (input == "") {
            //     this.register.email.error = "Cannot be empty";
            //     this.register.email.valide = false;
            // } else if (!input.match(email_regex)) {
            //     this.register.email.error = "One or some elements are missing in your email";
            //     this.register.email.valide = false;
            // } else {
            //     this.register.email.error = "";
            //     this.register.email.valide = true;
            // }
            // this.valideForm();
        },
        async sendLogin() {
            try {
                let {data: resp} = await this.$axios.post('/users/login', {
                    'email': this.email,
                    'password': this.password
                })
                localStorage.setItem('usr-token', resp.token);
                this.$store.commit('updateToken', resp.token);
                this.$router.push('/home');
            } catch (err) {
                console.log(err);
            }
        }
    }
})
</script>

<style scoped lang="scss">

</style>
