<template>
    <div id="login">
        <h3>Login</h3>
        <b-field label="E-mail" label-position="on-border" :type="login.email.error != '' ? 'is-danger' : ''" :message="login.email.error">
            <b-input type="email" @input="checkEmail($event)" v-model="login.email.value"></b-input>
        </b-field>
        <b-field label="Password" label-position="on-border" :type="login.password.error != '' ? 'is-danger' : ''" :message="login.password.error">
            <b-input type="password" @input="checkPassword()" password-reveal v-model="login.password.value"></b-input>
        </b-field>
        <div class="buttonContent">
            <div ref="loginButton" class="loginButton" @mouseover="validate == false ? moveButton() : ''">
                <b-button @click="validate == true ? sendLogin() : ''" :type="validate == true ? 'is-success is-light' : 'is-danger is-light'">Login</b-button>
            </div>
        </div>
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
            login: {
                email: {
                    value: "",
                    error: "",
                    valide: false
                },
                password: {
                    value: "",
                    error: "",
                    valide: false
                }
            },
            validate: false,
            timeout: false,
        }
    },
    methods: {
        valideForm() {
            if (this.login.email.valide == true &&
            this.login.password.valide == true) {
                this.$refs.loginButton.style.left = "50px";
                this.validate = true;
            } else
                this.validate = false;
        },
        moveButton() {
            let button = this.$refs.loginButton as HTMLElement;
            button.style.transitionProperty = "left";
            button.style.transitionDuration = "0.3s";
            if ((button.style.left == "100px" || button.style.left == "") && this.timeout == false) {
                button.style.left = "0px";
            } else if (this.timeout == false) {
                button.style.left = "100px"
            }
            this.timeout = true;
            window.setTimeout(() => {
                this.timeout = false;
            }, 290)
        },
        checkEmail(input) {
            const email_regex = (/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
            if (input == "") {
                this.login.email.error = "Cannot be empty";
                this.login.email.valide = false;
            } else if (!input.match(email_regex)) {
                this.login.email.error = "One or some elements are missing in your email";
                this.login.email.valide = false;
            } else {
                this.login.email.error = "";
                this.login.email.valide = true;
            }
            this.valideForm();
        },
        checkPassword() {
            if (this.login.password.value == "") {
                this.login.password.error = "Password cannot be empty"
                this.login.password.valide = false;
            } else {
                this.login.password.error = ""
                this.login.password.valide = true;
            }
            this.valideForm();
        },
        async sendLogin() {
            try {
                let {data: resp} = await this.$axios.post('/users/login', {
                    'email': this.login.email.value,
                    'password': this.login.password.value
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
#login {
    .buttonContent {
        transform: translate(calc(50% - 85px), 0%);
        position: relative;
        .loginButton {
            transition-property: left;
            transition-duration: 0.5s;
            width: fit-content;
            left: 50px;
            position: relative;
        }
    }
}
</style>
