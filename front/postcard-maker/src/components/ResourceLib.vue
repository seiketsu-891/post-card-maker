<template>
  <div class="outer">
    <div class="search-bar">
      <a-input-search
        v-model:value="searchKeyword"
        placeholder="输入关键字以快速搜索"
        enter-button="搜索"
        @search="onSearch"
      />
    </div>
    <!-- 插图集 -->
    <div class="scrollable">
      <a class="album__wrapper" v-for="a in albums" :key="a.id"
        ><span class="album__name">{{ a.name }}</span>
        <img class="album__cover" :src="a.url" />
        <p class="album__desc">{{ a.description }}</p>
      </a>
    </div>
  </div>
</template>
<script>
import { getAlbums } from "@/service/resources";
import { message } from "ant-design-vue";
export default {
  data() {
    return {
      searchKeyword: "",
      activeTabKey: 1,
      albums: [],
    };
  },

  created() {
    this.getAlbums();
  },
  methods: {
    async getAlbums() {
      const res = await getAlbums(1, 5);
      if (res.code == 200) {
        this.albums = res.data;
      } else {
        message.warn(res.message);
      }
    },
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
.outer
   height: 100%
.scrollable
   height: 100%
   overflow: auto
.search-bar
    margin-bottom: 20px
.album
   &__wrapper
    color: #4a4a4a
    display: inline-block
    border-radius: 10px
    padding: 20px 10px
    background: #EDF3F2
    margin-bottom: 30px
    display: flex
    flex-direction: column
    align-items: center
    &:hover
      box-shadow: rgba(0, 0, 0, 0.15) 2.4px 2.4px 3.2px
   &__cover
      display: block
      width: 200px
   &__name
      align-self: center
      font-size: 23px
</style>
