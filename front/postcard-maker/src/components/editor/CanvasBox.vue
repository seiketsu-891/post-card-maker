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
      currDimension: {
        width: 600,
        height: 400,
      },
    };
  },
  methods: {
    /**
     * 设置画布尺寸
     * @param {*} w 宽度
     * @param {*} h 高度
     */
    setCanvasSize(w, h) {
      this.canvas.setDimensions({ width: w, height: h });
    },
    /**
     * 初始化画布
     */
    initCanvas() {
      this.canvas = new this.fabric.Canvas(this.$refs.canvas);
      this.setCanvasSize(this.currDimension.width, this.currDimension.height);
    },
  },
  mounted() {
    this.initCanvas();
    // let prevZoom = 1.0;
    const _this = this;
    this.emitter.on("zoomCanvas", (args) => {
      const zoomFactor = args.zoom / 100;
      _this.canvas.setZoom(zoomFactor);
      _this.canvas.setDimensions({
        // 我这里之前犯的一个错是：
        // 以宽度为例， 再计算新宽度时，我是先拿到canvas.width， 然后再乘以factor。
        // 但实际上应该是用画布的真实宽度乘以factor，而不是用已经zoom过的宽度去乘factor
        width: this.currDimension.width * zoomFactor,
        height: this.currDimension.height * zoomFactor,
      });
    });
  },
  created() {
    // 监听画布信息改变事件，改变画布设置
    this.emitter.on("canvasChange", (arg) => {
      console.log(this.canvas);
      const data = arg.canvasInfo;
      this.currDimension.width = data.width;
      this.currDimension.height = data.height;
      this.setCanvasSize(data.width, data.height);
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
