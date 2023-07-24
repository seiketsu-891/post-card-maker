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
        <context-menu-sperator />
        <context-menu-item label="移至最上层" @click="sendObjToTop()" />
        <context-menu-sperator />
        <context-menu-item label="移至最下层" @click="sendObjToBottom()" />
      </context-menu>
    </div>
  </div>
</template>
<script>
import {
  savePostcard,
  getPostcard,
  performRedo,
  performUndo,
} from "@/service/postcard";
import { message } from "ant-design-vue";
export default {
  data() {
    return {
      currPostcardId: 0,
      constants: {
        CANVAS_DEFAULT_WIDHT: 600,
        CANVAS_DEFAULT_HEIGHT: 400,
        CANVAS_DEFAULT_BACKGROUND_COLOR: "#fff",
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
      // 保存用户每次缩放后的缩放比例
      zoomFactor: null,
    };
  },
  methods: {
    /**
     * 缩放画布
     */
    zoomCanvas(zoomFactor) {
      this.canvas.setZoom(zoomFactor);
      this.canvas.setDimensions({
        // 我这里之前犯的一个错是：
        // 以宽度为例， 再计算新宽度时，我是先拿到canvas.width， 然后再乘以factor。
        // 但实际上应该是用画布的真实宽度乘以factor，而不是用已经zoom过的宽度去乘factor
        width: this.currDimension.width * zoomFactor,
        height: this.currDimension.height * zoomFactor,
      });
    },
    /**
     * 元素下移一层
     */
    sendObjBackward() {
      const activeObj = this.canvas.getActiveObject();
      if (activeObj) {
        this.canvas.sendBackwards(activeObj);
        this.canvas.fire("object:modified", { target: activeObj });
      }
    },
    /**
     * 元素上移一层
     */
    sendObjForward() {
      const activeObj = this.canvas.getActiveObject();
      if (activeObj) {
        this.canvas.bringForward(activeObj);
        this.canvas.fire("object:modified", { target: activeObj });
      }
    },
    /**
     * 元素移至最上层
     */
    sendObjToTop() {
      const activeObj = this.canvas.getActiveObject();
      if (activeObj) {
        this.canvas.bringToFront(activeObj);
        this.canvas.fire("object:modified", { target: activeObj });
      }
    },
    /**
     * 元素移至最下层
     */
    sendObjToBottom() {
      const activeObj = this.canvas.getActiveObject();
      if (activeObj) {
        this.canvas.sendToBack(activeObj);
        this.canvas.fire("object:modified", { target: activeObj });
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
        this.savePostcardContent();
      }
    },
    /**
     * 设置画布尺寸
     * @param {*} w 宽度
     * @param {*} h 高度
     */
    setCanvasSize(w, h, zoomFactor) {
      this.currDimension.width = w;
      this.currDimension.height = h;
      if (zoomFactor) {
        this.canvas.setDimensions({
          width: w * zoomFactor,
          height: h * zoomFactor,
        });
      } else {
        this.canvas.setDimensions({ width: w, height: h });
      }
    },
    /**
     * 初始化画布
     */
    initCanvas() {
      this.canvas = new this.fabric.Canvas(this.$refs.canvas);
      // 监听元素修改事件
      this.canvas.on("object:modified", () => {
        console.log("modified");
        this.handleElementModified();
      });
    },
    setDefaultCanvasInfo() {
      const canvasInfo = {
        width: this.constants.CANVAS_DEFAULT_WIDHT,
        height: this.constants.CANVAS_DEFAULT_HEIGHT,
        currColor: this.constants.CANVAS_DEFAULT_BACKGROUND_COLOR,
        notSave: true,
      };
      this.emitter.emit("canvasChange", { canvasInfo });
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
    isLegalZoomFactorValue(z) {
      return z && typeof z === "number" && z <= 2 && z >= 0.25;
    },

    /**
     * 获取上一次编辑中的明信片相关信息
     */
    async getRecentPostcard() {
      const res = await getPostcard();
      if (res.code == "200") {
        const recentPostcard = res.data;
        if (!recentPostcard) {
          this.setDefaultCanvasInfo();
          return;
        }
        this.currPostcardId = recentPostcard.id;
        const contentObj = JSON.parse(recentPostcard.currContent);
        const canvasObj = JSON.parse(contentObj.canvas);
        const zoomFactor = this.$store.getters.canvasZoomValue;

        const canvasInfo = {
          width: contentObj.width,
          height: contentObj.height,
          currColor: canvasObj.background,
          notSave: true,
          zoomFactor: this.isLegalZoomFactorValue(zoomFactor)
            ? zoomFactor
            : null,
        };

        if (!recentPostcard.undoFlag) {
          this.emitter.emit("changeUndoStatus", { status: true });
        }
        if (!recentPostcard.RedoFlag) {
          this.emitter.emit("changeRedoStatus", { status: true });
        }

        this.changeCanvasInfo(canvasInfo);

        this.fabric.util.enlivenObjects(canvasObj.objects, (objects) => {
          objects.forEach((obj) => this.canvas.add(obj));
          this.canvas.renderAll();
        });
      } else {
        message.warn("读取数据失败");
      }
    },

    /**
     * 存储当前画布信息
     */
    async savePostcardContent() {
      this.emitter.emit("canvas-saving-start");
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
        canvas: JSON.stringify(this.canvas),
        width: this.currDimension.width,
        height: this.currDimension.height,
      };
      // 对于比较大的obj JSON.stringify会报栈溢出错误;
      // const content = JSON.stringify(contentObj);
      // 6/18
      const postcardContent = {
        content: JSON.stringify(contentObj),
        postcardId: this.currPostcardId ? this.currPostcardId : null,
      };
      const res = await savePostcard(postcardContent);
      if (res.code == "200") {
        this.emitter.emit("changeUndoStatus", { status: false });
        this.emitter.emit("canvas-saving-ok");
      } else {
        message.warn(res.message);
        // todo save failed  how to deal with it
      }
    },
    handleElementModified() {
      this.savePostcardContent();
    },

    /**
     * windo关闭前的操作
     */
    boforeUnloadHandler() {
      // 保存上次的zoom数据
      // 如果画布id是空白的（初始化未做元素操作的画布），那么不保存zoom数据
      if (this.currPostcardId) {
        this.$store.dispatch("updateZoomValue", +this.zoomFactor);
      }
    },
    changeCanvasInfo(canvasInfo) {
      this.currBackgroundColor = canvasInfo.currColor;
      this.canvas.setBackgroundColor(canvasInfo.currColor);

      if (
        canvasInfo.height !== this.currDimension.height ||
        canvasInfo.width !== this.currDimension.width
      ) {
        this.setCanvasSize(
          canvasInfo.width,
          canvasInfo.height,
          canvasInfo.zoomFactor
        );
      } else {
        this.canvas.renderAll(); // !important 似乎设置尺寸会自动重新render，但是设置背景色不会
      }
      // 发送明信片更新请求
      // 6/27 perf 在一开始空白画布和载入画布时不进行保存处理
      console.log("notSave?:" + canvasInfo.notSave);
      if (!canvasInfo.notSave) {
        this.savePostcardContent();
      } else {
        this.emitter.emit("updateInfoValue", { canvasInfo });
      }
    },
    /**
     * 撤销或重做
     */
    async undoOrRedo(type) {
      console.log("undo");
      let res;
      if (type === "undo") {
        res = await performUndo(this.currPostcardId);
      }
      if (type === "redo") {
        res = await performRedo(this.currPostcardId);
      }
      if (res && res.code == "200") {
        const postcard = res.data;
        if (!postcard) {
          message.warn("操作失败");
          return;
        }

        if (!postcard.undoFlag)
          this.emitter.emit("changeUndoStatus", { status: true });

        if (!postcard.redoFlag) {
          this.emitter.emit("changeRedoStatus", { status: true });
        }

        const contentObj = JSON.parse(postcard.currContent);
        const canvasObj = JSON.parse(contentObj.canvas);
        // const zoomFactor = this.$store.getters.canvasZoomValue;

        const canvasInfo = {
          width: contentObj.width,
          height: contentObj.height,
          currColor: canvasObj.background,
          notSave: true,
        };
        this.canvas.clear();
        this.changeCanvasInfo(canvasInfo);
        this.fabric.util.enlivenObjects(canvasObj.objects, (objects) => {
          objects.forEach((obj) => this.canvas.add(obj));
          this.canvas.renderAll();
        });
      } else {
        message.warn("操作失败");
      }
    },
  },
  watch: {},

  mounted() {
    this.initCanvas();
    this.emitter.on("zoomCanvas", (args) => {
      // 有时会出现0.0000000001这样的问题，所以保留2位小数
      const zoomFactor = (args.zoom / 100).toFixed(2);
      console.log(zoomFactor);
      this.zoomCanvas(zoomFactor);
      this.zoomFactor = zoomFactor;
    });
    // 监听画布新建事件
    this.emitter.on("initDefaultCanvas", () => {
      this.initCanvas();
      this.currPostcardId = null;
      this.setDefaultCanvasInfo();
      this.zoomCanvas(1);
      this.emitter.emit("zoomValueChange", {
        zoom: 100,
      });
    });
    // 监听保存画布事件
    this.emitter.on("save-canvas", () => {
      this.savePostcardContent();
    });
    // 监听画布转图片事件
    this.emitter.on("convertCanvasToImage", () => {
      this.convertCanvasToImage();
    });
    // 监听撤销/重做事件
    this.emitter.on("undoOrRedo", (args) => {
      if (!this.currPostcardId) {
        return;
      }
      this.undoOrRedo(args.type);
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
            fontSize: activeEle.get("fontSize"),
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
        // canvas监听modified事件，无法监听到此颜色变化
        // 手动触发
        ele.set("fill", newColor);
        this.canvas.fire("object:modified", { target: ele });
        this.canvas.renderAll();
      }
    });
    this.emitter.on("changeFont", (args) => {
      const ele = this.canvas.getActiveObject();
      ele.set("fontFamily", args.fontFamily);
      this.canvas.fire("object:modified", { target: ele });
      ele.fontId = args.fontId;
      this.canvas.renderAll();
    });

    this.emitter.on("changeFontSize", (args) => {
      console.log("change font size");
      console.log(args.size);
      const ele = this.canvas.getActiveObject();
      ele.set("fontSize", args.size);
      this.canvas.fire("object:modified", { target: ele });
      this.canvas.renderAll();
    });

    // window监听unload
    window.addEventListener("beforeunload", () => {
      this.boforeUnloadHandler();
    });
  },
  created() {
    // 监听画布信息改变事件，改变画布设置
    this.emitter.on("canvasChange", (args) => {
      this.changeCanvasInfo(args.canvasInfo);
    });
    // 监听插入图形事件，插入图形
    this.emitter.on("addShape", (args) => {
      const shape = args.shape;
      this.canvas.add(shape);
      this.savePostcardContent();
    });
    // 监听文本框插入事件
    this.emitter.on("addTextBox", (args) => {
      const textBox = args.textBox;
      this.canvas.add(textBox);
      this.savePostcardContent();
    });
    // 监听图片素材插入事件
    this.emitter.on("addImg", (args) => {
      const img = args.img;
      this.canvas.add(img);
      this.savePostcardContent();
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
