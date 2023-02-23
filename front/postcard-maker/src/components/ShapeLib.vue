<template>
  <div class="shape">
    <div v-for="s in shapes" :key="s.id">
      <a class="shape__wrapper" @click="addShape(s.name)">
        <img class="shape__img" :src="s.imgUrl"
      /></a>
    </div>
    <!-- <a href="#" @click="addShape('rect')"></a>
    <a href="#" @click="addShape('circle')">插入圆形</a>
    <a href="#" @click="addShape('triangle')">插入三角形</a> -->
  </div>
</template>
<script>
import { getShapes } from "@/service/resources";
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
  created() {
    this.getShapeList();
  },
  methods: {
    /**
     * 从后台获取形状
     */
    async getShapeList() {
      const res = await getShapes();
      console.log(res);
      if (res.code == "200") {
        console.log(res);
        this.shapes = res.data;
      }
    },
    /**
     * 在画布中插入图形
     */
    addShape(shapeName) {
      console.log(shapeName);
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
<style lang="sass" scoped>
.shape
  text-align: center
  &__wrapper
   width: fit-content
   display: inline-block
   border-radius: 10px
   padding: 30px
   background: #cbe2f3
   margin-bottom: 30px
   &:hover
    box-shadow: rgba(0, 0, 0, 0.15) 2.4px 2.4px 3.2px
  &__img
   width: 90px
</style>
