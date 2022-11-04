<template>
    <div id="EditArea">
        <p>Action param</p>
        <b-input></b-input>
        <b-button @click="deleteArea()">Delete</b-button>
        <b-button>Enable-disable</b-button>
        <p>Reaction param</p>
        <b-input></b-input>
    </div>
</template>

<script scoped lang="ts">
import vue from 'vue';
import _ from "lodash";


export default vue.extend({
    data() {
        return {
            refreshArea: false
        }
    },
    props: {
        id: Number,
    },
    methods: {
        async deleteArea(): Promise<void> {
            console.log(this.id)
            await this.$axios.delete("/areas/" + this.id, {
                headers: {
                    Authorization: this.$store.getters.userToken || "noToken",
                }
            })
            this.refreshArea = true;
            this.$emit('deleted', this.id)
        },
    }
});
</script>