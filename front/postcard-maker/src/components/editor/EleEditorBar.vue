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
      <a-input v-model:value="currSize" style="width: calc(100% - 200px)" />
      <a-button @click="changeFontSize(+1)"> + </a-button>
    </a-input-group>
    <!-- 颜色 -->
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
    });
    this.getFontList();
  },
  methods: {
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
      switch (operation) {
        case 1:
          this.currSize++;
          break;
        case -1:
          this.currSize--;
          break;
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
.editor
    &__item
        margin-right: 20px
        width: 189px
</style>
