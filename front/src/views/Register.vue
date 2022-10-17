<template>
    <div id="register">
        <h3>Register</h3>
        <section>
            <b-field label="First Name" label-position="on-border">
                <b-input v-model="register.firstName"></b-input>
            </b-field>
            <b-field label="Last Name" label-position="on-border">
                <b-input v-model="register.lastName" type="name"></b-input>
            </b-field>
            <b-field label="E-mail" label-position="on-border">
                <b-input type="email" v-model="register.email"></b-input>
            </b-field>
            <b-field label="Password" label-position="on-border">
                <b-input v-model="register.password" type="password" password-reveal></b-input>
            </b-field>
            <b-field label="Confirm password" label-position="on-border" :type="samePassword == true ? 'is-danger' : ''" :message="samePassword == true ? 'Password are not the same' : ''">
                <b-input v-model="register.confirmPassword" type="password" password-reveal></b-input>
            </b-field>
            <b-button @click="sendRegister()" type="is-primary">Register</b-button>
            <br/>
            <router-link to="/login">
                <a>Already an account ? Login</a>
            </router-link>
        </section>
    </div>
</template>

<script lang="ts">
import vue from 'vue';

export default vue.extend({
    data() {
        return {
            register: {
                firstName: "",
                lastName: "",
                email: "",
                password: "",
                confirmPassword: ""
            },
            samePassword: false,
            debounce: 0,
        }
    },
    components: {

    },
    watch: {
        'register.password': function() {
            if (this.register.confirmPassword != "") {
                this.checkSamePassword();
            }
        },
        'register.confirmPassword': function() {
            this.checkSamePassword();
        },
    },
    methods: {
        checkSamePassword() {
            if (this.register.password === this.register.confirmPassword) {
                this.samePassword = false;
            } else {
                this.samePassword = true;
            }
        },
        async sendRegister() {
            try {
                let {data: resp} = await this.$axios.post('/users', {
                    'firstName': this.register.firstName,
                    'lastName': this.register.lastName,
                    'email': this.register.email,
                    'password': this.register.password
                })
                localStorage.setItem('usr-token', resp.token);
                this.$store.commit('updateToken', resp.token);
                this.$router.push('/home');
            } catch (err) {
                if (err && err.reponse && err.response.data.statusCode == 400)
                    console.log("E-mail already taken...");
                console.log(err);
            }
        }
    }
})
</script>

<style scoped lang="scss">

</style>
