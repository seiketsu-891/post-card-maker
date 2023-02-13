<template>
  <div>
    <div class="search-bar">
      <a-input-search
        v-model:value="searchKeyword"
        placeholder="输入关键字以快速搜索"
        enter-button="搜索"
        @search="onSearch"
      />
    </div>
    <!-- 搜索框 -->

    <!-- 选项卡 -->
    <a-tabs v-model:activeKey="activeTabKey">
      <!-- 素材集 -->
      <a-tab-pane key="1">
        <template #tab> <span> 风格分类</span></template>
        <div><a href="#" @click="addStock()">点击插入图片</a></div></a-tab-pane
      >
      <!-- 主题分类 -->
      <a-tab-pane key="2">
        <template #tab> <span> 主题分类</span> </template>tab2</a-tab-pane
      >
    </a-tabs>
  </div>
</template>
<script>
export default {
  data() {
    return {
      searchKeyword: "",
      activeTabKey: 1,
    };
  },
  methods: {
    onSearch() {},
    /**
     * 在画布上插入所点击的图片素材
     */
    addStock() {
      const _this = this;
      const imgSource = require("../assets/logo.png");
      // 这个函数是静态函数
      // 第二个参数是callback
      // 第三个函数是图片设置信息
      this.fabric.Image.fromURL(
        imgSource,
        function (img) {
          _this.emitter.emit("addImg", { img });
        },
        {
          left: 0,
          top: 10,
        }
      );
    },
  },
};
</script>
<style lang="sass" scoped>
.search-bar
    margin-bottom: 20px
</style>
