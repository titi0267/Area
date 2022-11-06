<template>
    <div id="EditArea">
        <p>Action param</p>
        <b-input></b-input>
        <b-button @click="deleteArea()">Delete</b-button>
        <b-button @click="enableDisableArea()">Enable-disable</b-button>
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
        }
    },
    props: {
        id: Number,
    },
    methods: {
        async deleteArea(): Promise<void> {
            await this.$axios.delete("/areas/" + this.id, {
                headers: {
                    Authorization: this.$store.getters.userToken || "noToken",
                }
            })
            this.$emit('deleted')
        },
        async enableDisableArea(): Promise<void> {
            const {data : area} = await this.$axios.get("/areas/" + this.id, {
                headers: {
                    Authorization: this.$store.getters.userToken || "noToken",
                }
            })
            const newArea = {
                areaId: area.id,
                enabled: !area.enabled,
            }
            console.log(newArea)
            await this.$axios.put("/areas/", newArea, {
                headers: {
                    Authorization: this.$store.getters.userToken || "noToken",
                }
            })
        }
    }
});
</script>