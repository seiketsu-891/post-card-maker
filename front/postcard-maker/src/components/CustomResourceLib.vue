<template>
  <div class="outer">
    <a-upload
      v-model:file-list="fileList"
      name="avatar"
      list-type="picture-card"
      class="avatar-uploader"
      :show-upload-list="false"
      :customRequest="customUpload"
      :before-upload="beforeUpload"
    >
      <div>
        <loading-outlined v-if="isUploading"></loading-outlined>
        <plus-outlined v-else></plus-outlined>
        <div class="ant-upload-text">上传</div>
      </div>
    </a-upload>

    <div class="scrollable">
      <div class="pics__container">
        <a class="pics__wrapper" v-for="pic in pictures" :key="pic.id">
          <img :src="pic.url" class="pics__img" @click="addStock(pic.url)"
        /></a>
        <InfiniteLoading @infinite="getPics" :key="loadingKey">
          <template #complete> <span></span> </template>
        </InfiniteLoading>
      </div>
    </div>
  </div>
</template>
<script>
import { LoadingOutlined, PlusOutlined } from "@ant-design/icons-vue";
import { message, Upload } from "ant-design-vue";
import { uploadFile, getUserPics } from "@/service/resources";
export default {
  components: {
    LoadingOutlined,
    PlusOutlined,
  },
  data() {
    return {
      loadingKey: 0,
      fileList: [],
      pictures: [], // 当前用户所上传的所有图片
      isUploading: false, // 是否在上传中
      pageNum: 1,
    };
  },
  mounted() {},
  methods: {
    /**
     * 获取用户上传的图片
     */
    async getPics($state) {
      console.log("scroll");
      const res = await getUserPics(this.pageNum, 5);
      if (res.code == 200) {
        this.count = res.data.total;
        if (res.data.list.length < 5) {
          $state.complete();
        }
        this.pictures.push(...res.data.list);
        this.pageNum++;
      } else {
        message.error(res.message);
      }
    },
    /**
     * 图片上传前的处理
     */
    beforeUpload(file) {
      const isPNG = file.type === "image/png";
      if (!isPNG) {
        message.error("请上传png格式");
      }
      const sizeInLimit = file.size / 1024 / 1024 < 1;
      if (!sizeInLimit) {
        message.error("文件不能大于1MB");
      }
      return (isPNG && sizeInLimit) || Upload.LIST_IGNORE;
    },

    /**
     * 自定义上传接口
     */
    async customUpload(file) {
      this.isUploading = true;
      const form = new FormData();
      form.append("file", file.file);
      const res = await uploadFile(form);
      if (res.code == "200") {
        this.isUploading = false;
        message.success("上传成功");
        this.loadingKey++;
        this.pageNum = 1;
        this.pictures = [];
        // this.getPics();
      } else {
        message.error("上传失败");
        this.isUploading = false;
      }
    },
    /**
     *
     */
    addStock(url) {
      const _this = this;
      this.fabric.Image.fromURL(
        url,
        function (img) {
          _this.emitter.emit("addImg", { img });
        },
        {
          left: 0,
          top: 10,
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
.pics
  &__container
      width: 100%
      text-align: center
      padding: 20px
      margin-bottom:40px
  &__wrapper
      display: block
      padding: 10px
      margin-bottom: 30px
      display: flex
      justify-content: center
      &:hover
        box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px
  &__img
      display: block
      width: 140px
</style>
