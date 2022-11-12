<template>
    <div id="EditArea" :style="{ 'background' : `linear-gradient(to right, ${action ? action.backgroundColor : ''}, ${reaction ? reaction.backgroundColor : ''})` }">
        <div class="action">
            <div class="action-param" v-if="this.area['actionParamName'] !== ''">
                <p>Action parameter</p>
                <b-input ref="action" :autofocus="true" :value="area.actionParam" @input="debounceUpdate($event, 'action')"></b-input>
            </div>
            <p v-else> No action parameter </p>
        </div>
        <div class="manage">
            <div class="edit">
                <b-icon icon="trash" @click.native="deleteArea()"></b-icon>
            </div>
            <b-switch size="is-medium" passive-type="is-danger" type="is-success" v-model="area.enabled" @input="postArea()"></b-switch>
        </div>
        <div class="reaction">
            <div class="reaction-param" v-if="this.area['reactionParamName'] !== ''">
                <p>Reaction parameter</p>
                <b-input ref="reaction" :value="area.reactionParam" @input="debounceUpdate($event, 'reaction')"></b-input>
            </div>
            <p v-else> No reaction parameter </p>
        </div>
    </div>
</template>

<script scoped lang="ts">
import vue from 'vue';
import _ from "lodash";

export default vue.extend({
    props: {
        area: Object /** Object that contains the area creation fields */,
        action: Object, /** Current action */
        reaction: Object, /** Current reaction */
    },
    methods: {
        /**
         * It's a function that delete area in back-end server.
         * @data {Object} area
         * @async
         */
        async deleteArea(): Promise<void> {
            try {
                await this.$axios.delete("/areas/" + this.area.id, {
                    headers: {
                        Authorization: this.$store.getters.userToken || "noToken",
                    }
                })
                this.notification('Your area has been deleted', 'is-success');
                this.$emit('deleted')
            } catch {
                this.notification('Failed to delete your area', 'is-danger');
            }
        },
        /**
         * It's a function that wait 400ms before post the new values in back-end server.
         * @data {Object} area
         */
        debounceUpdate: _.debounce(function(input, type: string): void {
            this.area[type + 'Param'] = input;
            this.postArea()
            this.$refs[type].focus()
        }, 400),
        /**
         * It's a function that post the new values in back-end server.
         * @data {Object} area
         * @async
         */
        async postArea(): Promise<void> {
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
                this.notification("Your area has been updated !", "is-success");
            } catch {
                this.notification("Failed to update your area", "is-danger");
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
    .action,
    .reaction {
        width: 85%;
        color: white;
        display: flex;
        text-align: start;
        flex-direction: column;
        justify-content: center;
        align-items: flex-start;
        >p {
            text-align: center;
            width: 100%;
            font-family: "Avenir Roman";
            font-size: 20px;
        }
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
    }
    .reaction {
        .reaction-param {
            text-align: end;
            p {
                text-align: end;
                width: 100%;
            }
        }
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
            color: #2c3e50;
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