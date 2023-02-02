<template>
  <div class="wrapper">
    <a-slider
      class="slider"
      v-model:value="zoomBarValue"
      :max="200"
      :min="25"
      :step="5"
      :tooltipVisible="false"
      :disabled="zoomBarDisabled"
    ></a-slider>
    <span class="zoomBar__value">{{ zoomBarValue }} %</span>
  </div>
</template>
<script>
export default {
  props: {
    // zoomBarPassedValue: {
    //   type: Number,
    //   required: true,
    //   default: 100,
    // },
  },
  watch: {
    zoomBarValue(value) {
      // zoom值改变后提醒画布组件进行缩放
      this.emitter.emit("zoomCanvas", { zoom: value });
    },
  },
  data() {
    return {
      zoomBarDisabled: false,
      zoomBarValue: 100,
    };
  },
  mounted() {
    this.emitter.on("zoomValueChange", (args) => {
      const value = args.zoom;
      if (this.zoomBarValue !== value) {
        this.zoomBarValue = value;
      }
    });
  },
};
</script>
<style lang="sass" scoped>
.wrapper
    display: flex
    align-items: center
    justify-content: center
    width: 300px
    .slider
        width: 70%
        margin-right: 10px
</style>
