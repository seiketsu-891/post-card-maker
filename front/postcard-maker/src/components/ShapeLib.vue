<template>
  <div>
    <a href="#" @click="addShape('rect')">插入长方形</a>
    <a href="#" @click="addShape('circle')">插入圆形</a>
    <a href="#" @click="addShape('triangle')">插入三角形</a>
  </div>
</template>
<script>
export default {
  data() {
    return {
      // 常量
      constants: {
        DEFAULT_WIDTH: 150,
        DEFAULT_HEIGHT: 100,
      },
      commonArgs: {
        left: 20,
        top: 20,
        fill: "pink",
      },
      // 从后台获取的形状数据
      shapes: [{}],
    };
  },
  mounted() {},
  methods: {
    /**
     * 在画布中插入图形
     */
    addShape(shapeName) {
      let shape;
      switch (shapeName) {
        case "rect":
          shape = this.initRect();
          break;
        case "circle":
          shape = this.initCircle();
          break;
        case "triangle":
          shape = this.initTriangle();
          break;
      }
      if (shape) {
        this.emitter.emit("addShape", { shape });
      }
    },
    /**
     * 创建方形
     */
    initRect() {
      return new this.fabric.Rect({
        ...this.commonArgs,
        ...{
          width: this.constants.DEFAULT_WIDTH,
          height: this.constants.DEFAULT_HEIGHT,
        },
      });
    },
    /**
     * 创建圆形
     */
    initCircle() {
      return new this.fabric.Circle({
        ...this.commonArgs,
        ...{
          radius: this.constants.DEFAULT_WIDTH / 2,
        },
      });
    },
    /**
     * 创建三角形
     */
    initTriangle() {
      return new this.fabric.Triangle({
        ...this.commonArgs,
        ...{
          width: this.constants.DEFAULT_WIDTH,
          height: this.constants.DEFAULT_HEIGHT,
        },
      });
    },
  },
};
</script>
<style></style>
