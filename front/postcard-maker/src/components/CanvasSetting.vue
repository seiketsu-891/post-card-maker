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
      <a-form-item>
        <a-checkbox
          value="1"
          name="keepRatio"
          v-model:checked="formSetting.keepRatio"
          >保持比例</a-checkbox
        ></a-form-item
      >
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
export default {
  data() {
    return {
      // 画布设置表单数据
      formSetting: {
        keepRatio: true,
      },
      canvasInfo: {
        width: 300,
        height: 300,
        currColor: "#eb4165", // 画布背景色
      },
    };
  },
  methods: {
    /**
     * 点击画布设置提交按钮的处理
     */
    handleSumbitBtnClicked() {
      // 传值给canvas组件以改变画布样式
      this.emitter.emit("canvasChange", {
        canvasInfo: this.canvasInfo,
      });
    },
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
