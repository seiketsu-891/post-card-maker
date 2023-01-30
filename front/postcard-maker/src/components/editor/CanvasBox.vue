<template>
  <div
    class="canvas-wrapper"
    :style="{
      width: width + 'px',
      height: height + 'px',
      backgroundColor: backgroundColor,
    }"
  ></div>
</template>
<script>
export default {
  data() {
    return {
      width: 700,
      height: 500,
      backgroundColor: "#fff",
    };
  },
  //   props: {
  //     width: {
  //       type: Number,
  //       default: 700,
  //     },
  //     height: {
  //       type: Number,
  //       default: 500,
  //     },
  //     backgroundColor: {
  //       type: String,
  //       default: "#fff",
  //     },
  //   },
  created() {
    this.emitter.on("canvasChange", (arg) => {
      const data = arg.canvasInfo;
      this.width = data.width;
      this.height = data.height;
      this.backgroundColor = data.currColor;
    });
  },
};
</script>
<style lang="sass" scoped>
.canvas-wrapper
    width: 100px
    height: 100px
    background-color: #fff
</style>
