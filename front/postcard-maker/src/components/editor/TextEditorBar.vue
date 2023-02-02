<template>
  <div class="wrapper">
    <!-- 改变字体 -->
    <a-select
      class="editor__item"
      v-model:value="currFont"
      :options="fonts"
    ></a-select>
    <!-- 改变字号 -->
    <a-input-group compact class="editor__item">
      <a-button @click="changeFontSize(-1)"> -</a-button>
      <a-input v-model:value="currSize" style="width: calc(100% - 200px)" />
      <a-button @click="changeFontSize(+1)"> + </a-button>
    </a-input-group>
    <!-- 颜色 -->
    <div>
      <color-picker v-model:pureColor="currColor"></color-picker>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      currFont: "Arial",
      fonts: [
        {
          value: "1",
          label: "Arial",
        },
      ],
      currSize: "10",
      currColor: "#673bb7",
    };
  },
  methods: {
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
</style>
