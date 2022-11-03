<template>
    <div id="NavBar">
        <b-image class="logo" :src="require('@/assets/area_logo.png')"/>
        <div class="links">
            <a @click="$router.push('/home')">Home</a>
            <a @click="$router.push('/create/action'), removeLocalStorageItem()">Create</a>
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
                            <b-icon icon="user"></b-icon>
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
</template>

<script scoped lang="ts">
import vue from 'vue';

export default vue.extend({
    data() {
        return {
            user: [],
            navigation: "",
        }
    },
    mounted() {
        this.getUserInfos();
    },
    watch: {
        '$route.name': function(): void {
            this.checkRoute();
        }
    },
    methods: {
        removeLocalStorageItem(): void {
            localStorage.removeItem('area');
        },
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
    width: 100%;
    border-bottom: 1px solid rgb(202, 202, 202);
    position: fixed;
    background-color: white;
    z-index: 10;
    .logo {
        position: relative;
        :deep(img) {
            height: $navbar-height;
            width: auto;
        }
    }
    .links {
        a:first-child {
            margin-right: 15px;
        }
        a {
            color: white;
            font-family: Bebas Regular;
            font-size: 30px;
            background-color: #37474F;
            padding: 3px 6px;
            &:hover {
                color: #7DCEA0;
            }
        }

    }
    .profile {
        width: 206.53px;
        display: flex;
        justify-content: flex-end;
        >:deep(div) {
            margin-right: 15px;
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
