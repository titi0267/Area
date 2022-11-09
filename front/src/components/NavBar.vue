<template>
    <div id="NavBar">
        <b-image class="logo" :src="require('@/assets/area_logo.png')"/>
        <div class="links">
            <a @click="$router.push('/home')" :style="{ 'color' : ($route.path == '/home' ? '#19191a' : '') }">Home</a>
            <a @click="createRedirect()" :style="{ 'color' : ($route.path.includes('create') ? '#19191a' : '') }">Create</a>
        </div>
        <div class="right">
            <div class="APK">
                <b-icon @click.native="getApk()" icon="download"/>
                <p>Download APP</p>
            </div>
            <div class="profile">
                <b-dropdown
                    v-model="navigation"
                    position="is-bottom-left"
                    append-to-body
                    aria-role="menu">
                    <template #trigger>
                        <a class="navbarItem"
                            role="button">
                            <div class="userIcon">
                                <b-icon icon="circle-user" size="is-large"></b-icon>
                            </div>
                        </a>
                    </template>
                    <b-dropdown-item custom aria-role="profileItem">
                        Logged as <b> {{ user.firstName }} {{ user.lastName }} </b>
                    </b-dropdown-item>
                    <hr class="dropdown-divider">
                    <b-dropdown-item value="profile" class="dropItem" @click="$router.push('/profile')">
                        <b-icon icon="address-card"></b-icon>
                        Profile
                    </b-dropdown-item>
                    <b-dropdown-item value="services" class="dropItem" @click="$router.push('/services')">
                        <b-icon icon="link"></b-icon>
                        Services
                    </b-dropdown-item>
                    <hr class="dropdown-divider">
                    <b-dropdown-item value="logout" aria-role="profileItem" @click="logout()" class="dropItem">
                        <b-icon icon="right-from-bracket"></b-icon>
                        Logout
                    </b-dropdown-item>
                </b-dropdown>
            </div>
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
            navigation: "", /** Current open page on user dropdown */
        }
    },
    mounted() {
        this.getUserInfos();
    },
    watch: {
        /**
         * A watcher that is called when the route name changes.
         */
        '$route.name': function(): void {
            this.checkRoute();
        }
    },
    methods: {
        async getApk(): Promise<void> {
            window.location.href = './client.txt';
        },
        /**
         * A method that is called when the user clicks on the create button. It removes the area from the local storage and redirects the user to the create page.
         */
        createRedirect(): void {
            localStorage.removeItem('area');
            this.$router.push('/tmp')
            this.$router.push('/create/action')
        },
        /**
         * Checking the route name and setting the navigation variable to the route name.
         * @data {String} navigation
         */
        checkRoute(): void {
            if (this.$route.name == 'profile')
                this.navigation = "profile";
            else if (this.$route.name == 'services')
                this.navigation = "services";
            else
                this.navigation = "";
        },
        /**
         * A method that is called when the user clicks on the logout button.
         */
        logout(): void {
            this.$store.commit('updateToken', '');
            localStorage.setItem('usr-token', '');
            this.$router.push('/login');
        },
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
$navbar-height: 75px;

#NavBar {
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color: white;
    height: $navbar-height;
    position: fixed;
    z-index: 10;
    margin: 10px;
    width: calc(100% - 20px);
    box-shadow: 0 0 15px 1px #00000099;
    border-radius: 20px;
    .logo {
        margin-left: 20px;
        position: relative;
        :deep(img) {
            height: calc($navbar-height - 10px);
            width: auto;
        }
    }
    .links {
        position: relative;
        top: 3px;
        a:first-child {
            margin-right: 15px;
        }
        a {
            font-size: 25px;
            color: #e5e4e4;
            text-transform: uppercase;
            font-family: Avenir Roman;
            height: 32px;
            &:hover {
                color: #626263;
            }
        }

    }
    .right {
        display: flex;
        justify-content: flex-end;
        align-items: center;
        width: 206.53px;
        .APK {
            font-size: 13px;
            p {
                top: 9px;
                position: relative;
                font-family: Avenir Roman;
            }
            :deep(.icon) {
                top: 9px;
                position: relative;
                cursor: pointer;
            }
        }
        .profile {
            right: -12px;
            position: relative;
            display: flex;
            justify-content: flex-end;
            >:deep(div) {
                .icon {
                    color: rgb(25 25 66 / 81%);
                }
                margin-right: 23px;
                svg {
                    width: 34px;
                    height: 34px;
                }
            }
        }
    }
}

.dropItem {
    display: flex;
    align-content: center;
    justify-content: flex-start;
    span {
        margin-right: 15px;
    }
}


</style>