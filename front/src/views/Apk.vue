<template>
  <div><a href="./apk/client.apk" download id="apk" /></div>
</template>

<script lang="ts">
import vue from "vue";

export default vue.extend({
  mounted() {
    const downloadLink = document.getElementById("apk");

    if (!downloadLink) return;

    downloadLink.click();
    this.$router.push("/home");
  },
});
</script>

<style scoped lang="scss"></style>
