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
        <template #tab> <span> 风格分类</span></template
        >tab1</a-tab-pane
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
};
</script>
<style lang="sass" scoped>
.search-bar
    margin-bottom: 20px
</style>
