<template>
  <div>
    <!-- 画布设置表单 -->
    <a-form class="canvasform">
      <a-form-item label="画布宽度">
        <a-input v-model:value="canvasInfo.width"></a-input>
      </a-form-item>
      <a-form-item label="画布高度">
        <a-input v-model:value="canvasInfo.height"></a-input>
      </a-form-item>
      <a-form-item label="画布背景">
        <div class="canvasform__color-picker-wrapper">
          <color-picker
            class="canvasform__colorpicker"
            v-model:pureColor="canvasInfo.currColor"
            shape="circle"
          ></color-picker>
        </div>
      </a-form-item>
      <div class="canvasform__btns">
        <a-button type="primary" @click="handleSumbitBtnClicked">提交</a-button>
      </div>
    </a-form>
  </div>
</template>
<script>
import { shallowEqual, shallowCopy } from "@/assets/js/util.js";
export default {
  data() {
    return {
      // 画布设置表单数据
      formSetting: {
        keepRatio: true,
      },
      canvasInfo: {
        width: "",
        height: "",
        currColor: "#fff", // 画布背景色
      },
      infoCopy: {
        width: "",
        height: "",
        currColor: "#fff",
      },
    };
  },
  methods: {
    /**
     * 点击画布设置提交按钮的处理
     */
    handleSumbitBtnClicked() {
      if (shallowEqual(this.canvasInfo, this.infoCopy)) {
        return;
      }
      // 传值给canvas组件以改变画布样式
      this.emitter.emit("canvasChange", {
        canvasInfo: this.canvasInfo,
      });
      if (
        this.canvasInfo.width !== this.infoCopy.width ||
        this.canvasInfo.height !== this.infoCopy.height
      ) {
        // 改变zoom bar上的值为100
        this.emitter.emit("zoomValueChange", { zoom: 100 });
      }
      shallowCopy(this.infoCopy, this.canvasInfo);
    },
  },

  created() {
    this.emitter.on("updateInfoValue", (args) => {
      const info = args.canvasInfo;
      if (shallowEqual(info, this.canvasInfo)) {
        return;
      }
      info.isFirstLoaded = false;
      shallowCopy(this.canvasInfo, info);
      shallowCopy(this.infoCopy, info);
    });
  },
};
</script>
<style lang="sass" scoped>
.canvasform
    &__btns
        margin-top: 50px
    &__btn
        &--preview
            margin-right: 30px
</style>
