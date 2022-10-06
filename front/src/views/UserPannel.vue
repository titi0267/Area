<template>
    <div id="userPannel">
        <h3>User Pannel</h3>
        <router-link to="/create-area">
            <b-button style="is-primary">Create</b-button>
        </router-link>
        <div v-for="area in areas" :key="area['id'] + area['actionService']" class="box">
            <div class="action">
                {{ area['actionService'] }}
                {{ area['action'] }}
            </div>
            <div class="reaction">
                {{ area['reactionService'] }}
                {{ area['reaction'] }}
            </div>
        </div>
        <div class="box">

        </div>
    </div>
</template>

<script lang="ts">
import vue from 'vue';

export default vue.extend({
    data() {
        return {
            areas: {},
        }
    },
    mounted() {9
        this.getUserAreas();
    },
    components: {

    },
    methods: {
        getUserAreas() {
            try {
                let { data: resp } = this.$axios.get('/users/areas', {
                    headers: {
                        Authorization: this.$store.getters.userToken,
                    },
                })
                this.areas = resp;
            } catch (err) {
                console.log(err);
            }
        }
    }
})
</script>

<style lang="scss">
.box {
    width: 100%;
    height: 30px;
    border: 1px solid black;
}
</style>
