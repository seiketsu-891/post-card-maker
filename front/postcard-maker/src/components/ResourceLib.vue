<template>
  <div class="outer">
    <div class="search-bar">
      <a-input-search
        v-model:value="searchKeyword"
        placeholder="输入关键字以快速搜索"
        enter-button="搜索"
        @search="handleSearchBtnClicked"
      />
    </div>
    <!-- 插图部分容器 -->
    <div class="scrollable">
      <!-- 插图集 -->
      <div v-if="showAlbums">
        <a
          @click="handleAlbumClicked(a.id)"
          class="album__wrapper"
          v-for="a in albums"
          :key="a.id"
          ><span class="album__name">{{ a.name }}</span>
          <img class="album__cover" :src="a.url" />
          <p class="album__desc">{{ a.description }}</p>
        </a>
      </div>
      <!-- 单张图显示区 -->
      <div class="pics__container" v-if="!showAlbums">
        <div class="pics__btn-wrapper">
          <a-button
            type="dashed"
            @click="showAlbums = true"
            class="pics__btn--return"
            >返回插图集</a-button
          >
        </div>
        <div class="pics__empty" v-show="emptyState"><a-empty></a-empty></div>
        <div class="pics__wrapper" v-for="pic in pictures" :key="pic.id">
          <a>
            <img
              :src="pic.url"
              class="pics__img"
              @click="addStock(pic.albumId, pic.id, pic.url)"
          /></a>
          <div class="pics__vip-icon">
            <CrownFilled class="icon--crown" v-show="pic.isVip" />
          </div>
        </div>
        <InfiniteLoading @infinite="getPicList" :key="loadingKey">
          <!-- 覆盖掉默认的提示信息-->
          <template #complete> <span></span> </template>
        </InfiniteLoading>
      </div>
    </div>
  </div>
</template>
<script>
import { CrownFilled } from "@ant-design/icons-vue";
import { getAlbums, checkPicAuth } from "@/service/resources";
import { getIllustrations } from "@/service/resources";
import { message } from "ant-design-vue";
export default {
  components: {
    CrownFilled,
  },
  data() {
    return {
      loadingKey: 0,
      showAlbums: true, // 当前是否显示图片集（当搜索图片和点击图片集后显示的都是单张图片）
      searchKeyword: "",
      albums: [], // 从后端拿来的图片集数据
      pictures: [], // 搜索结果或某个图片集里的图片数据
      pageNum: 1,
      curAlbumId: "", // 控制从后端获取图片时的图片集筛选
      emptyState: false,
    };
  },

  created() {
    this.getAlbums();
  },
  methods: {
    /**
     * 点击搜索后的处理
     */
    handleSearchBtnClicked() {
      this.emptyState = false;
      this.loadingKey++;
      this.pageNum = 1;
      this.showAlbums = false;
      this.curAlbumId = "";
      this.pictures = [];
    },
    /**
     * 点击图片集后的处理
     */
    handleAlbumClicked(id) {
      this.emptyState = false;
      this.searchKeyword = "";
      this.pageNum = 1;
      this.showAlbums = false;
      this.curAlbumId = id;
      // 每次点击不同的图片集时，强制清空数据并重新load
      // 如果不加下面这行会造成每个图片集显示的都是第一次点击的图片集内的图片
      this.pictures = [];
    },
    /**
     * 获取图片集数据
     */
    async getAlbums() {
      const res = await getAlbums(1, 5);
      if (res.code == 200) {
        this.albums = res.data;
      } else {
        message.warn(res.message);
      }
    },
    /**
     * 获取图片数据
     */
    async getPicList($state) {
      const res = await getIllustrations(
        this.pageNum,
        5,
        this.curAlbumId,
        this.searchKeyword
      );
      if (res.code == 200) {
        console.log(res.data);
        if (res.data.total < 1) {
          this.emptyState = true;
          $state.complete();
        }
        if (res.data.list.length < 5) {
          $state.complete();
        }
        this.pictures.push(...res.data.list);
        this.pageNum++;
      } else {
        message.warn(res.message);
      }
    },
    onSearch() {},
    /**
     * 在画布上插入所点击的图片素材
     */
    async addStock(albumId, id, url) {
      let hasAuth = false;
      const res = await checkPicAuth(albumId, id);
      if (res.code == 200) {
        hasAuth = res.data;
        console.log(res);
      } else {
        message.warn("未知错误");
        return;
      }

      if (!hasAuth) {
        message.warn("该图片仅限会员使用");
      }
      const _this = this;
      // 这个函数是静态函数
      // 第二个参数是callback
      // 第三个函数是图片设置信息
      this.fabric.Image.fromURL(
        url,
        function (img) {
          _this.emitter.emit("addImg", { img });
        },
        {
          left: 0,
          top: 10,
          // 不加这个会报错Tainted canvases may not be exported
          crossOrigin: "anonymous",
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
   overflow-y: auto
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
.pics
  &__empty
     margin-top: 50px
  &__container
      width: 100%
      text-align: center
  &__wrapper
      text-align: center
      width: 100%
      display: block
      position: relative
  &__vip-icon
     position: absolute
     top: 0
     left:2px
  &__btn-wrapper
      width: 100%
      text-align: left
  &__btn
    &--return
      margin-bottom: 10px
  &__img
      width: 90%
      &:hover
        box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px
.icon--crown
  color: #FFD039
  font-size: 30px
</style>
