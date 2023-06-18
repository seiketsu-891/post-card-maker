<template>
  <div class="canvas-wrapper" @contextmenu.prevent="onRightClick">
    <canvas ref="canvas"></canvas>
    <div>
      <context-menu
        v-model:show="elementContextMenuVisibility"
        :options="contextMemuOptions"
      >
        <context-menu-item label="删除元素" @click="delObj()" />
        <context-menu-sperator />
        <context-menu-item label="上移一层" @click="sendObjForward()" />
        <context-menu-sperator />
        <context-menu-item label="下移一层" @click="sendObjBackward()" />
      </context-menu>
    </div>
  </div>
</template>
<script>
import { savePostcard, getPostcard } from "@/service/postcard";
import { message } from "ant-design-vue";
export default {
  data() {
    return {
      // 当前页面的elements信息
      pageElements: {
        shapes: [],
      },
      constants: {
        CANVAS_DEFAULT_WIDHT: 600,
        CANVAS_DEFAULT_HEIGHT: 400,
      },
      elementContextMenuVisibility: false,
      // 画布当前被设置的宽高（每次zoom时以此为基准）
      currDimension: {
        width: 0,
        height: 0,
      },
      currBackgroundColor: "#fff",
      contextMemuOptions: { zIndex: 3, minWidth: 230, x: 500, y: 200 },
      activeEle: null, // 当前被选中的画布元素
      // 数据中的elements
      elements: {
        shapes: [],
        texts: [],
        illustrations: [],
      },
      eles: [],
    };
  },
  methods: {
    /**
     * 缩放画布
     */
    zoomCanvas(zoomFactor) {
      console.log(zoomFactor);
      this.canvas.setZoom(zoomFactor);
      this.canvas.setDimensions({
        // 我这里之前犯的一个错是：
        // 以宽度为例， 再计算新宽度时，我是先拿到canvas.width， 然后再乘以factor。
        // 但实际上应该是用画布的真实宽度乘以factor，而不是用已经zoom过的宽度去乘factor
        width: this.currDimension.width * zoomFactor,
        height: this.currDimension.height * zoomFactor,
      });
    },
    sendObjBackward() {
      const activeObj = this.canvas.getActiveObject();
      if (activeObj) {
        this.canvas.sendBackwards(activeObj);
      }
    },
    sendObjForward() {
      const activeObj = this.canvas.getActiveObject();
      if (activeObj) {
        this.canvas.bringForward(activeObj);
      }
    },
    addElements() {
      const _this = this;
      // 加入形状
      const shapes = this.elements.shapes;
      if (shapes != null && shapes.length > 0) {
        shapes.forEach((s) => {
          let shape;
          switch (s.type) {
            case "0":
              shape = new this.fabric.Rect({
                width: s.width,
                height: s.height,
              });
              break;
            case "1":
              shape = new this.fabric.Circle({
                radius: s.width / 2,
              });
              break;
            default:
              shape = new this.fabric.Rect({
                width: s.width,
                height: s.height,
                fill: s.fill,
                left: s.left,
                top: s.top,
              });
          }
          shape.fill = s.fill;
          this.canvas.add(shape);
        });
      } // end shapes
      // 加入插图
      const images = this.elements.illustrations;
      if (images != null && images.length > 0) {
        images.forEach((i) => {
          this.fabric.Image.fromURL(
            i.url,
            function (img) {
              _this.emitter.emit("addImg", { img });
            },
            {
              left: 0,
              top: 10,
            }
          );
        });
      }
      // 加入文字
      const texts = this.elements.texts;
      if (texts != null && texts.length > 0) {
        // 这里我一开始在if外面放了一个text变量，
        // 结果出现的问题是所有textBox的text属性都是一样的

        texts.forEach((t) => {
          let text = new this.fabric.IText(t.content, {
            left: t.left,
            top: t.top,
            // fontSize: t.fontSize,
            fontFamily: t.fontFamily,
            fill: t.fill,
            // fontSize: t.fontSize,
            selectable: true,
          });
          this.canvas.add(text);
        });
      }
    },
    /**
     * 右键点击后的处理
     */
    onRightClick(e) {
      // 如果当前有元素被选中，在点击位置显示菜单
      const activeObj = this.canvas.getActiveObject();
      if (activeObj) {
        this.elementContextMenuVisibility = true;
        this.contextMemuOptions.x = e.x;
        this.contextMemuOptions.y = e.y;
      }
    },
    sendObjToBack() {
      const activObj = this.canvas.getActiveObject();
      if (activObj) {
        this.canvas.sendToBack(activObj);
        // todo 从elemetns中移除
      }
    },

    /**
     * 删除画布元素
     */
    delObj() {
      const activObj = this.canvas.getActiveObject();
      if (activObj) {
        this.canvas.remove(activObj);
        // todo 从elemetns中移除
      }
    },
    /**
     * 设置画布尺寸
     * @param {*} w 宽度
     * @param {*} h 高度
     */
    setCanvasSize(w, h) {
      this.currDimension.width = w;
      this.currDimension.height = h;
      this.canvas.setDimensions({ width: w, height: h });
    },
    /**
     * 初始化画布
     */
    initCanvas() {
      this.canvas = new this.fabric.Canvas(this.$refs.canvas);
      console.log(100);
      this.setCanvasSize(
        this.constants.CANVAS_DEFAULT_WIDHT,
        this.constants.CANVAS_DEFAULT_HEIGHT
      );

      // 画布必须设置初始颜色，不然下载下来的图片背景会是灰色;
      this.canvas.setBackgroundColor(this.currBackgroundColor);
    },
    /**
     * 将画布转化为图片并下载
     */
    convertCanvasToImage() {
      const dataURL = this.canvas.toDataURL({
        width: this.canvas.width,
        height: this.canvas.height,
        left: 0,
        top: 0,
        format: "image/jpeg",
        quality: 1,
      });
      const link = document.createElement("a");
      link.download = "image.jpeg";
      link.href = dataURL;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },
    /**
     * 获取上一次编辑中的明信片相关信息
     */
    async getRecentPostcard() {
      const res = await getPostcard();
      if (res.code == "200") {
        const recentPostcard = res.data;
        if (!recentPostcard || !recentPostcard.currContent) {
          return;
        }
        const contentObj = JSON.parse(recentPostcard.currContent);
        const canvasObj = contentObj.canvas;
        const canvasInfo = {
          width: contentObj.width,
          height: contentObj.height,
          currColor: canvasObj.background,
        };
        this.emitter.emit("canvasChange", {
          canvasInfo,
          isFirstLoaded: true,
        });

        this.fabric.util.enlivenObjects(canvasObj.objects, (objects) => {
          objects.forEach((obj) => this.canvas.add(obj));
          this.canvas.renderAll();
        });
      } else {
        message.warn(res.message);
      }
    },

    /**
     * 存储当前画布信息
     */
    async savePostcardContent() {
      // 如果仅仅使用this.canvas，则不会存储画布的宽度和高度信息
      // JSON.stringify方法默认会忽略对象的函数和不可枚举的属性
      // 但使用下面方法也无法正确序列化出宽度和高度， canvas是特殊obj
      // Object.defineProperties(currCanvas, {
      //   height: {
      //     value: this.currDimension.height,
      //     enumerable: true,
      //   },
      //   width: {
      //     value: this.currDimension.width,
      //     enumerable: true,
      //   },
      // });
      const contentObj = {
        canvas: this.canvas,
        width: this.currDimension.width,
        height: this.currDimension.height,
      };
      const postcardContent = {
        content: JSON.stringify(contentObj),
      };
      const res = await savePostcard(postcardContent);
      if (res.code == "200") {
        console.log(res);
      } else {
        message.warn(res.message);
      }
    },
  },
  mounted() {
    this.initCanvas();
    // let prevZoom = 1.0;
    this.emitter.on("zoomCanvas", (args) => {
      const zoomFactor = args.zoom / 100;
      this.zoomCanvas(zoomFactor);
    });
    // 监听画布转图片事件
    this.emitter.on("convertCanvasToImage", () => {
      this.convertCanvasToImage();
    });
    // 加入上次编辑中的画布元素
    this.addElements();
    // 给画布元素绑定点击事件
    this.canvas.on("mouse:down", (e) => {
      const activeEle = e.target;
      if (!activeEle || activeEle.type === "image") {
        //  令元素编辑组件消失
        this.emitter.emit("hideEleEditor", {});
        return;
      }
      // 判断元素类型
      const type = activeEle.type;
      if (type === "i-text") {
        this.emitter.emit("showEleEditor", {
          type: "i-text",
          properties: {
            color: activeEle.fill,
            fontFamily: activeEle.fontFamily,
          },
        });
        // todo 显示文字编辑框
      } else {
        //  显示形状颜色修改提示
        this.emitter.emit("showEleEditor", {
          type: "shape",
          properties: {
            color: activeEle.fill,
          },
        });
      }
    });
    // 监听元素颜色改变事件
    this.emitter.on("changeActiveEleColor", (args) => {
      const newColor = args.color;
      // 获取当前活跃元素
      const ele = this.canvas.getActiveObject();
      if (ele && ele.fill) {
        // 这里直接用xx.fill = xx的话，虽然会改变属性值，但不起作用
        ele.set("fill", newColor);
        this.canvas.renderAll();
      }
    });
    this.emitter.on("changeFont", (args) => {
      const ele = this.canvas.getActiveObject();
      ele.set("fontFamily", args.fontFamily);
      ele.fontId = args.fontId;
      this.canvas.renderAll();
    });
  },
  created() {
    // 监听画布信息改变事件，改变画布设置
    this.emitter.on("canvasChange", (arg) => {
      const canvasInfo = arg.canvasInfo;
      // this.currDimension.width = canvasInfo.width;
      // this.currDimension.height = canvasInfo.height;
      this.currBackgroundColor = canvasInfo.currColor;
      this.setCanvasSize(canvasInfo.width, canvasInfo.height);
      this.canvas.setBackgroundColor(canvasInfo.currColor);
      // 发送明信片更新请求
      if (!canvasInfo.isFirstLoaded) {
        this.savePostcardContent();
      }
    });
    // 监听插入图形事件，插入图形
    this.emitter.on("addShape", (args) => {
      const shape = args.shape;
      this.pageElements.shapes.push(shape);
      shape.on("modified", () => {
        console.log(shape);
      });
      // this.elements.push(shape);
      this.canvas.add(shape);
    });
    // 监听文本框插入事件
    this.emitter.on("addTextBox", (args) => {
      const textBox = args.textBox;
      this.pageElements.shapes.push(textBox);
      textBox.on("modified", () => {
        console.log("文本框发生改变");
        console.log(textBox);
      });
      this.canvas.add(textBox);
    });
    // 监听图片素材插入事件
    this.emitter.on("addImg", (args) => {
      const img = args.img;
      this.pageElements.shapes.push(img);
      img.on("modified", () => {
        console.log("图片发生改变");
        console.log(img);
      });
      this.canvas.add(img);
    });
    this.getRecentPostcard();
  },
};
</script>
<style lang="sass" scoped>
.canvas-wrapper
    background-color: #fff
    .context-menu
      positon: absolute
</style>
