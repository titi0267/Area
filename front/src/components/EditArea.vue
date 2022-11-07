<template>
    <div id="EditArea" :style="{ 'background' : `linear-gradient(to right, ${action ? action.backgroundColor : ''}, ${reaction ? reaction.backgroundColor : ''})` }">
        <div class="action-param">
            <p>Action parameter</p>
            <b-input v-model="area.actionParam"></b-input>
        </div>
        <div class="manage">
            <div class="edit">
                <b-icon icon="trash" @click.native="deleteArea()"></b-icon>
            </div>
            <b-switch size="is-medium" passive-type="is-danger" type="is-success" v-model="area.enabled" @input="enableDisableArea()"></b-switch>
        </div>
        <div class="reaction-param">
            <p>Reaction parameter</p>
            <b-input v-model="area.actionParam"></b-input>
        </div>
    </div>
</template>

<script scoped lang="ts">
import vue from 'vue';

export default vue.extend({
    data() {
        return {
        }
    },
    props: {
        area: Object,
        action: Object,
        reaction: Object,
    },
    methods: {
        async deleteArea(): Promise<void> {
            await this.$axios.delete("/areas/" + this.area.id, {
                headers: {
                    Authorization: this.$store.getters.userToken || "noToken",
                }
            })
            this.$emit('deleted')
        },
        async enableDisableArea(): Promise<void> {
            try {
                await this.$axios.put("/areas/", {
                    areaId: this.area.id,
                    enabled: this.area.enabled,
                    actionParam: this.area.actionParam,
                    reactionParam: this.area.reactionParam
                }, {
                    headers: {
                        Authorization: this.$store.getters.userToken || "noToken",
                    }
                })
            } catch {
                
            }
        }
    }
});
</script>

<style scoped lang="scss">
#EditArea {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin: auto;
    margin-bottom: 30px;
    width: 90%;
    background-color: red;
    height: 110px;
    padding: 10px 30px;
    border-radius: 20px;
    .action-param,
    .reaction-param {
        color: white;
        font-family: "Avenir Roman";
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        justify-content: center;
        text-align: start;
        width: 100%;
        :deep(.control) {
        width: 100%;
            input {
                width: 80%;
            }
        }
    }
    .reaction-param {
        align-items: flex-end;
        text-align: end;
    }
    .manage {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        align-items: center;
        :deep(label) {
            margin-right: 0px;
        }
        .edit {
            display: flex;
            align-items: center;
            text-transform: uppercase;
            :deep(span) {
                padding: 20px;
                box-shadow: 0 0 15px 1px #000000a1;
                background-color: white;
                border-radius: 50%;
                cursor: pointer;
                svg {
                    width: 19px;
                    height: 19px;
                }
            }
        }
    }
}
</style>