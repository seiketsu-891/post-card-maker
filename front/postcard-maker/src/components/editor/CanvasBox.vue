<template>
  <div class="canvas-wrapper">
    <canvas ref="canvas"></canvas>
  </div>
</template>
<script>
export default {
  data() {
    return {
      backgroundColor: "#fff",
    };
  },
  mounted() {
    this.canvas = new this.fabric.Canvas(this.$refs.canvas);
    console.log(this.canvas);
  },
  created() {
    // 监听画布信息改变事件，改变画布设置
    this.emitter.on("canvasChange", (arg) => {
      console.log(this.canvas);
      const data = arg.canvasInfo;
      this.canvas.setDimensions({ width: data.width, height: data.height });
      this.canvas.setBackgroundColor(data.currColor);
    });
    // 监听插入图形事件，插入图形
    this.emitter.on("addShape", (args) => {
      const shape = args.shape;
      this.canvas.add(shape);
    });
    // 监听文本框插入事件
    this.emitter.on("addTextBox", (args) => {
      const textBox = args.textBox;
      console.log(this.canvas);
      this.canvas.add(textBox);
    });
    // 监听图片素材插入事件
    this.emitter.on("addImg", (args) => {
      const img = args.img;
      this.canvas.add(img);
    });
  },
};
</script>
<style lang="sass" scoped>
.canvas-wrapper
    background-color: #fff
</style>
