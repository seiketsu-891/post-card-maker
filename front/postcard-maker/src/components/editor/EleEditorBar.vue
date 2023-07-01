<template>
  <!-- 这里如果有v-show的话，color pick似乎会被不正确渲染，需要 -->
  <div class="wrapper" v-if="show">
    <!-- 改变字体 -->
    <a-select
      v-show="isEditingText"
      class="editor__item"
      v-model:value="currFont"
      :options="fonts"
      @change="changeFont"
    ></a-select>
    <!-- 改变字号 -->
    <a-input-group compact class="editor__item" v-show="isEditingText">
      <a-button @click="changeFontSize(-1)"> -</a-button>
      <a-input-number
        :controls="false"
        min="1"
        max="500"
        v-model:value="currSize"
        @pressEnter="onFontSizeInputChanged"
      />
      <a-button @click="changeFontSize(1)"> + </a-button>
    </a-input-group>
    <!-- 颜色 -->
    <!-- todo 手动输入字号按回车后可以改变字号 -->
    <div>
      <color-picker
        v-model:pureColor="currColor"
        disableAlpha
        @pureColorChange="onColorchange"
      ></color-picker>
    </div>
  </div>
</template>
<script>
import { getFonts } from "@/service/resources";
export default {
  data() {
    return {
      // 记录改变字号请求发送后的时间
      prevFontSizeRequestTime: [],
      fontSizeTimeoutRequest: [],
      isEditingText: false, // 控制是否显示仅编辑文字时需要的部分
      show: false,
      currFont: "Arial",
      fonts: [],
      currSize: "10",
      currColor: "#673bb7",
    };
  },
  created() {
    this.emitter.on("hideEleEditor", () => {
      this.show = false;
    });
    this.emitter.on("showEleEditor", (args) => {
      const eleProperties = args.properties;
      if (eleProperties.color) {
        this.currColor = eleProperties.color;
      }
      this.show = true;
      this.isEditingText = args.type === "i-text";
      this.currSize = eleProperties.fontSize;
    });
    this.getFontList();
  },
  methods: {
    onFontSizeInputChanged() {
      this.emitter.emit("changeFontSize", { size: this.currSize });
    },
    changeFont(value) {
      let fontFamily;
      this.fonts.forEach((f) => {
        if (f.value === value) {
          fontFamily = f.label;
        }
      });
      this.emitter.emit("changeFont", {
        fontId: value,
        fontFamily,
      });
    },
    async getFontList() {
      const res = await getFonts();
      if (res.code == 200) {
        this.fonts = res.data;
      }
    },
    onColorchange() {
      this.emitter.emit("changeActiveEleColor", { color: this.currColor });
    },
    changeFontSize(operation) {
      let isCoolDown = false;
      if (operation != 1 && operation != -1) {
        return;
      }
      switch (operation) {
        case 1:
          this.currSize++;
          break;
        case -1:
          this.currSize--;
          break;
      }
      const currTime = new Date().getTime();
      const timeDiff = currTime - this.prevFontSizeRequestTime;
      if (timeDiff < 500) {
        isCoolDown = true;
      }
      if (!isCoolDown) {
        this.emitter.emit("changeFontSize", { size: this.currSize });
        this.prevFontSizeRequestTime = new Date().getTime();
      } else {
        // 还在冷却期，此时不发送电文，而改为在冷却期后发送电文
        // compare time
        const coolDownTimeLeft = 500 - timeDiff;
        const timeout = setTimeout(() => {
          if (!isCoolDown) {
            this.emitter.emit("changeFontSize", { size: this.currSize });
          }
        }, coolDownTimeLeft);
        this.fontSizeTimeoutRequest.push(timeout);
      }
      // 每次触发，不管是否有发送电文，都清除前面在等待的
      // clear all timeouts
      if (this.fontSizeTimeoutRequest.length > 0) {
        for (let i = 0; i < this.fontSizeTimeoutRequest.length; i++) {
          clearTimeout(this.fontSizeTimeoutRequest[i]);
        }
        this.fontSizeTimeoutRequest = [];
      }
    },
  },
};
</script>
<style lang="sass" scoped>
.wrapper
    display: flex
    align-items: center
    justify-content: center
    width: max-content
    height: 20px
    padding: 30px
    box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px
    background:  rgba(255, 255, 255, 0.45)
    border-radius: 30px
    z-index: 999
.editor
    &__item
        margin-right: 20px
        width: 189px
</style>
