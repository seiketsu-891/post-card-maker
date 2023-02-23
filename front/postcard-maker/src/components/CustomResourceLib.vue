<template>
  <a-upload
    v-model:file-list="fileList"
    name="avatar"
    list-type="picture-card"
    class="avatar-uploader"
    :show-upload-list="false"
    :customRequest="customUpload"
    :before-upload="beforeUpload"
    @change="handleChange"
  >
    <img v-if="imageUrl" :src="imageUrl" alt="avatar" />
    <div v-else>
      <loading-outlined v-if="isUploading"></loading-outlined>
      <plus-outlined v-else></plus-outlined>
      <div class="ant-upload-text">上传</div>
    </div>
  </a-upload>
</template>
<script>
import { LoadingOutlined, PlusOutlined } from "@ant-design/icons-vue";
import { message, Upload } from "ant-design-vue";
import { uploadFile } from "@/service/resources";
export default {
  components: {
    LoadingOutlined,
    PlusOutlined,
  },
  data() {
    return {
      fileList: [
        {
          uid: "-1",
          name: "image.png",
          status: "done",
          url: "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png",
        },
      ],
      userPics: [], // 当前用户所上传的所有图片
      isUploading: false, // 是否在上传中
    };
  },
  methods: {
    /**
     * 图片上传前的处理
     */
    beforeUpload(file) {
      const isPNG = file.type === "image/png";
      if (!isPNG) {
        message.error("请上传png格式");
      }
      return isPNG || Upload.LIST_IGNORE;
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
        message.success("上传成功");
      } else {
        message.error("上传失败");
      }
      this.isUploading = false;
    },
  },
};
</script>
