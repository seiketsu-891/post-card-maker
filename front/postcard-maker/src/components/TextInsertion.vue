<template>
  <div>
    <div class="text">
      <a-button type="primary" @click="addTextBox()">新增文本框</a-button>
      <a-divider orientation="left"
        ><span class="divider-txt">预设样式</span></a-divider
      >
    </div>
  </div>
</template>
<script>
export default {
  methods: {
    addTextBox() {
      const textBox = new this.fabric.IText("请输入文字", {
        left: 20,
        top: 20,
        fontFamily: "Arial",
        fill: "black",
      });
      this.emitter.emit("addTextBox", { textBox });
    },
  },
};
</script>
<style lang="sass" scoped>
.divider-txt
   font-size: 14px
   color: #58595A
</style>
