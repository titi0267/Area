<template>
    <div id="login">
        <section class="loginForm">
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
            <p class="or">or</p>
            <div class="googleOauth">
                <b-button @click="getGoogleOauthLogin()"> <b-image :src="require('@/assets/google_logo.png')"> </b-image> Login with Google </b-button>
            </div>
            <div class="register">
                <router-link to="/register">
                    <a>No account ? Register</a>
                </router-link>
            </div>
        </section>
    </div>
</template>

<script lang="ts">
import vue from 'vue';
import { Login } from '../types/index'

export default vue.extend({
    data() {
        return {
            login: { /** It's a variable that contains the login form. */
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
            } as Login,
            validate: false, /** If this variable is true all the fields form are valides. */
            timeout: false, /** This variable is used to block the time of the button animation */
        }
    },
    mounted() {
        if (localStorage.getItem('google-oauth') == 'true')
            this.postGoogleOauth();
    },
    methods: {
        /**
         * It's a function that check if the user is authenticated and post the oauth code to the server.
         * @async
         */
        async postGoogleOauth(): Promise<void> {
            const code: string = this.$route.query.code;

            if (code == null || code == undefined) {
                this.notification("Your authentification has failed", 'is-danger');
                localStorage.removeItem('google-oauth')
                return;
            }
            try {
                let {data: resp} = await this.$axios.post("/oauth/google/register", {
                    code: code
                })
                localStorage.removeItem('google-oauth')
                localStorage.setItem('usr-token', resp.token);
                this.$store.commit('updateToken', resp.token);
                this.$router.push('/home');
            } catch {
                this.notification("Your authentification has failed", 'is-danger');
                localStorage.removeItem('google-oauth')
            }

        },
        /**
         * It's a function that get the google oauth url for login.
         * @async
         */
        async getGoogleOauthLogin(): Promise<void> {
            try {
                const {data: url} = await this.$axios.get("/oauth/google/register", {
                    headers: {
                        Authorization: this.$store.getters.userToken || "noToken",
                    },
                })
                localStorage.setItem('google-oauth', 'true');
                window.location.href = url;
            } catch {
                this.notification("Internal server error", 'is-danger');
            }
        },
        /**
         * It's a function that check if the entire form is valide or not.
         * @data {Object} login
         * @data {Boolean} validate
         */
        valideForm(): void {
            if (this.login.email.valide == true &&
            this.login.password.valide == true) {
                this.$refs.loginButton.style.left = "50px";
                this.validate = true;
            } else
                this.validate = false;
        },
        /**
         * It's a function that move the button to the left or to the right when the form is not valid.
         * @data {Boolean} timeout
         */
        moveButton(): void {
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
        /**
         * It's a function that check if the email is valide or not.
         * @param {String} input - Text input
         * @data {Object} login
         */
        checkEmail(input: String): void {
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
        /**
         * It's a function that check if the password is empty or not.
         * @data {Object} login
         */
        checkPassword(): void {
            if (this.login.password.value == "") {
                this.login.password.error = "Password cannot be empty"
                this.login.password.valide = false;
            } else {
                this.login.password.error = ""
                this.login.password.valide = true;
            }
            this.valideForm();
        },
        /**
         * It's a function that send the login form to the server.
         * @data {Object} login
         * @async
         */
        async sendLogin(): Promise<any> {
            try {
                let {data: resp} = await this.$axios.post('/users/login', {
                    'email': this.login.email.value,
                    'password': this.login.password.value
                })
                localStorage.setItem('usr-token', resp.token);
                this.$store.commit('updateToken', resp.token);
                this.$router.push('/home');
            } catch (err) {
                if (err.response)
                    this.notification(err.response.data.message, 'is-danger');
            }
        }
    }
})
</script>

<style scoped lang="scss">
#login {
    .loginForm {
        background-color: white;
        min-width: 400px;
        width: 450px;
        height: fit-content;
        position: absolute;
        box-shadow: 0 0 30px 1px black;
        border-radius: 20px;
        padding: 20px 25px;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        h3 {
            font-size: 40px;
            margin-bottom: 25px;
            font-family: Bebas Regular;
        }
        :deep(.field) {
            margin-bottom: 20px;
        }
        .register {
            margin-top: 15px;
        }
    }
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
    .or {
        margin-top: 15px;
        font-family: Century Gothic Regular;
    }
    .googleOauth {
        margin-top: 15px;
        :deep(button) {
            background-color: #537ebf;
            width: 250px;
            font-family: Century Gothic Regular;
            color: white;
            font-weight: 700;
            span {
                display: flex;
                flex-direction: row;
                align-items: center;
                justify-content: flex-start;
            }
            figure {
                height: 30px;
                width: auto;
                margin-right: 15px;
                img {
                    height: 100%;
                    width: auto;
                }
            }
        }
    }
}
</style>
