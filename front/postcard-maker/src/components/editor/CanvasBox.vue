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
        width: this.CANVAS_DEFAULT_WIDHT,
        height: this.CANVAS_DEFAULT_HEIGHT,
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
        console.log(this.canvas.getObjects());
        this.canvas.sendToBack(activObj);
        console.log(this.canvas.getObjects());
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
      this.canvas.setDimensions({ width: w, height: h });
    },
    /**
     * 初始化画布
     */
    initCanvas() {
      this.canvas = new this.fabric.Canvas(this.$refs.canvas);
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
    getCanvasElements() {
      const recentPostcardProject = {
        projectId: 1,
        canvas: {
          width: 500,
          height: 300,
          backgroundColor: "#3af4f2",
        },
        texts: [
          {
            content: "测试",
            left: 20,
            top: 20,
            fill: "red",
            // fontSize: 20,
            fontFamily: "Helvetica",
          },
          {
            content: "测试",
            left: 0,
            top: 0,
            fontFamily: "Verdana",
            fill: "pink",
          },
          {
            content: "测试",
            left: 20,
            top: 20,
            fontFamily: "Times New Roman",
            fill: "pink",
          },
        ],
        shapes: [
          {
            type: "0",
            fill: "orange",
            width: 150,
            height: 100,
            left: 250.8708407237088,
            top: -68.57772591354359,
            angle: 81.01263599260606,
            scaleX: 2.6869004311767037,
            scaleY: 2.6869004311767037,
            zoomX: 5.3738008623534075,
            zoomY: 5.3738008623534075,
          },
          {
            type: "1",
            fill: "black",
            width: 100,
            height: 22,
            left: 10,
            top: 50,
          },
          {
            type: "0",
            fill: "red",
            width: 100,
            height: 22,
            left: 10,
            top: 50,
          },
          {
            type: "0",
            fill: "yellow",
            width: 100,
            height: 22,
            left: 10,
            top: 50,
          },
        ],
        illustrations: [
          {
            width: 100,
            height: 100,
            url: "https://tpc.googlesyndication.com/simgad/10235212382014818733",
            left: 30,
            top: 30,
          },
        ],
      };
      if (recentPostcardProject) {
        // 设置画布信息
        const canvasDb = recentPostcardProject.canvas;
        this.currDimension.width = canvasDb.width;
        this.currDimension.height = canvasDb.height;
        this.currBackgroundColor = canvasDb.backgroundColor;
        this.elements.shapes = recentPostcardProject.shapes;
        this.elements.texts = recentPostcardProject.texts;
        this.elements.illustrations = recentPostcardProject.illustrations;
      }
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
      console.log(type);
      if (type === "i-text") {
        console.log("show");
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
      console.log(ele);
      if (ele && ele.fill) {
        console.log("color" + ele.fill);
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
      const data = arg.canvasInfo;
      this.currDimension.width = data.width;
      this.currDimension.height = data.height;
      this.currBackgroundColor = data.currColor;
      this.setCanvasSize(data.width, data.height);
      this.canvas.setBackgroundColor(data.currColor);
    });
    // 监听插入图形事件，插入图形
    this.emitter.on("addShape", (args) => {
      const shape = args.shape;
      this.pageElements.shapes.push(shape);
      shape.on("modified", () => {
        console.log("形状发生改变");
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
    this.getCanvasElements();
  },
};
</script>
<style lang="sass" scoped>
.canvas-wrapper
    background-color: #fff
    .context-menu
      positon: absolute
</style>
