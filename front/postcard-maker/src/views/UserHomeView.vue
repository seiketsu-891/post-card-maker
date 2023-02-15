<template>
  <div class="container">
    <!-- 头部 -->
    <a-layout>
      <a-layout-header class="header">
        <!-- 网站标题 -->
        <h1 class="header__sitename">
          Post<span class="header__sitename--specialchar">C</span>ard
          <span class="header__sitename--underlined"
            >M<span class="header__sitename--specialchar">a</span>ker</span
          >
        </h1>
        <!-- 按钮 -->
        <div class="header__btns">
          <a-avatar class="header__avatar" style="background-color: #82c646">{{
            user.username.substring(0, 1)
          }}</a-avatar>
          <a-button class="header__btn--get-premium header__btn">
            <template #icon> <CrownFilled class="icon--crown" /> </template>
            <span v-if="isPremium">您是会员</span
            ><span v-else>成为会员</span></a-button
          >
          <a-button
            @click="handleDownloadClicked()"
            class="header__btn--download header__btn"
          >
            <template #icon>
              <CloudDownloadOutlined class="icon--downloand" />
            </template>
            下载文件</a-button
          >
        </div>
        <!-- 头部菜单 -->
      </a-layout-header>
    </a-layout>
    <!-- 中间区域 -->
    <a-layout>
      <!-- 侧边导航 -->
      <a-layout-sider class="side" width="min-content">
        <!-- 画布项 -->
        <a-menu class="side__menu" v-model:selectedKeys="selectedMenuItemKey">
          <a-menu-item key="0" @click="handleSideMenuItemClicked(0)">
            <template #icon>
              <CopyOutlined />
            </template>
            画布
          </a-menu-item>
          <!-- 插图项 -->
          <a-menu-item key="1" @click="handleSideMenuItemClicked(1)">
            <template #icon>
              <FileImageOutlined />
            </template>
            插图
          </a-menu-item>
          <!-- 形状项 -->
          <a-menu-item key="2" @click="handleSideMenuItemClicked(2)">
            <template #icon>
              <StarOutlined />
            </template>
            形状
          </a-menu-item>
          <!-- 文字项 -->
          <a-menu-item key="3" @click="handleSideMenuItemClicked(3)">
            <template #icon>
              <FontSizeOutlined />
            </template>
            文字
          </a-menu-item>
          <!-- 上传项 -->
          <a-menu-item key="4" @click="handleSideMenuItemClicked(4)">
            <template #icon>
              <UploadOutlined />
            </template>
            上传
          </a-menu-item>
          <!-- 我的项 -->
          <a-menu-item
            key="5"
            class="menu-item"
            @click="handleSideMenuItemClicked(5)"
          >
            <template #icon>
              <solutionOutlined />
            </template>
            我的
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <!-- 主要内容区 -->
      <a-layout-content class="main">
        <!-- 资源选择区域（可折叠） -->
        <div class="resource" v-show="resourceAreaVisible">
          <div class="resource__container">
            <!-- 画布设置 -->
            <CanvasSetting v-show="activeMenu == menus[0]" />
            <!-- 素材选择 -->
            <ResourceLib v-show="activeMenu == menus[1]" />
            <!-- 形状选择 -->
            <ShapeLib v-show="activeMenu == menus[2]" />
            <!-- 文字框选择 -->
            <TextInsertion v-show="activeMenu == menus[3]" />
            <!-- 自定义上传素材 -->
            <CustomResourceLib v-show="activeMenu == menus[4]" />
            <!-- 已保存的文件 -->
            <MyPostcards v-show="activeMenu == menus[5]" />
          </div>
          <!-- 折叠按钮 -->
          <button class="resource__collapsebtn" @click="collapeResourceArea">
            <LeftOutlined />
          </button>
        </div>
        <!-- 明信片编辑区 -->
        <div class="editor" @wheel.prevent="onWheelScrolledInEditor($event)">
          <TextEditorBar class="text-editor-bar" />
          <div class="canvas-container">
            <div class="canvas"><CanvasBox /></div>
          </div>
          <!-- 放大缩小的条 -->
          <div class="zoomBar">
            <ZoomBar ref="zoomBar" />
          </div></div
      ></a-layout-content>
    </a-layout>
  </div>
</template>
<script>
import CanvasSetting from "@/components/CanvasSetting";
import ResourceLib from "@/components/ResourceLib";
import ShapeLib from "@/components/ShapeLib";
import TextInsertion from "@/components/TextInsertion";
import CustomResourceLib from "@/components/CustomResourceLib";
import MyPostcards from "@/components/MyPostcards";
import CanvasBox from "@/components/editor/CanvasBox";
import ZoomBar from "@/components/editor/ZoomBar";
import TextEditorBar from "@/components/editor/TextEditorBar";
// 图标
import {
  FontSizeOutlined,
  UploadOutlined,
  SolutionOutlined,
  StarOutlined,
  CopyOutlined,
  CrownFilled,
  CloudDownloadOutlined,
  LeftOutlined,
  FileImageOutlined,
} from "@ant-design/icons-vue";
export default {
  components: {
    FontSizeOutlined,
    UploadOutlined,
    SolutionOutlined,
    TextInsertion,
    CopyOutlined,
    CrownFilled,
    CloudDownloadOutlined,
    FileImageOutlined,
    StarOutlined,
    LeftOutlined,
    CanvasSetting,
    ResourceLib,
    CustomResourceLib,
    MyPostcards,
    CanvasBox,
    ShapeLib,
    ZoomBar,
    TextEditorBar,
  },
  data() {
    return {
      selectedMenuItemKey: ["0"], // 用于设置默认选中项
      menus: [
        "canvasSetting",
        "resoureLib",
        "textBox",
        "customResource",
        "myPostcards",
      ], // 菜单名，主要用于切换菜单右侧展开部分
      activeMenu: "canvasSetting", // 当前点击的菜单
      resourceAreaVisible: true, // 侧边可折叠资源菜单区是否可见
      isPremium: true,
    };
  },
  computed: {
    user() {
      return this.$store.getters.user;
    },
  },
  created() {
    // todo check ifVIP and set the value of isPremium;
  },
  methods: {
    /**
     * 隐藏侧边可折叠菜单区
     */
    collapeResourceArea() {
      this.resourceAreaVisible = false;
    },
    /**
     * 点击侧边菜单项的处理
     * @param {*} menuItemIndex // 菜单项的index，根据index值设置当前active的菜单
     */
    handleSideMenuItemClicked(menuItemIndex) {
      if (!this.resourceAreaVisible) {
        this.resourceAreaVisible = true;
      }
      this.activeMenu = this.menus[menuItemIndex];
    },
    /**
     * 在画布编辑区域容器内滑动鼠标滚轮后的处理
     * 计算出zoom的新值，如果是有效值则传递给ZoomBar组件
     */
    onWheelScrolledInEditor(e) {
      e.stopPropagation();
      const delta = e.deltaY;
      // 拿到上次的zoom值，在上次的zoom值基础上判定新的zoom值
      let zoom = this.$refs.zoomBar.zoomBarValue / 100;
      zoom *= 0.999 ** delta;
      // 设定在25%到200%的范围内缩放
      if (zoom > 2) zoom = 2;
      if (zoom < 0.25) zoom = 0.25;
      // 如果先toFixed(2)，然后乘以100的话，有时会出现末尾还是有0.00000000000001的情况，所以这里用Math.round
      zoom = Math.round(zoom * 100);
      this.emitter.emit("zoomValueChange", { zoom });
    },
    /**
     * 点击下载按钮后的处理
     */
    handleDownloadClicked() {
      // todo 发送请求判定是否是vip
      this.emitter.emit("convertCanvasToImage", {});
    },
  },
};
</script>
<style lang="sass" scoped>
.container
    width: 100%
    height: 100vh
    position: fixed
.header
  background: #3494E6
  background: -webkit-linear-gradient(to right, #EC6EAD, #3494E6)
  background: linear-gradient(to left, #EC6EAD, #3494E6)
  height: 60px
  position: fixed
  width: 100%
  display: flex
  justify-content: space-between
  &__sitename
      color: #fff
      &--specialchar
        color: #F1C2EC
      &--underlined
        text-decoration: underline
        text-underline-offset: 4px
        text-decoration-color: #C2C2EE
  &__btns
     display: flex
     align-items: center
  &__avatar
     margin-right: 15px
     cursor: pointer
  &__btn
      outline: none
      border: none
      height: 40px
      margin-right: 40px
      padding: 0 20px
      &--get-premium
         color: #fff
         background-color: rgba(255,255,255,.2)
         &:hover
            background-color: rgba(255,255,255,.2)
            color: #fff
         &:active
            background-color: rgba(255,255,255,.2)
            color: #fff
         &:focus
            background-color: rgba(255,255,255,.2)
            color: #fff
      &--download
         color: #4A4A4A
         margin-right: 0
.main
    margin-top: 60px
    margin-left: 120px
    height: calc(100vh - 60px)
    background-color:#EBECF0
    display: flex
    .resource
      flex-shrink: 0
      width: 300px
      height: 100%
      background-color: #fff
      // background-color: #343536
      position: relative
      padding: 40px 25px
      box-shadow: 25px 0 20px -20px rgba(101, 138, 216, 0.2)
      &__collapsebtn
        background-color: rgba(101, 138, 216, 1)
        position: absolute
        right: -30px
        top: 50%
        margin-top: -50px
        width: 30px
        height: 70px
        border: 0
        border-radius: 0 10px 10px 0
        text-align: center
        cursor: pointer
        color: #fff
        // box-shadow: 25px 0 20px -20px rgba(101, 138, 216, 0.)
    .editor
      position: relative
      width: 100%
      height: 100%
      overflow: scroll
      padding: 80px
      // 这里如果在editor中直接设置flex的话，会导致垂直方向padding无法正常显示，因此需要给canvas再添加一个父容器
      .canvas-container
        min-width: 100%
        min-height: 100%
        display: flex
        align-items: center
        justify-content: center
        // 如果不添加float，当水平方向overflow时，右边的padding会消失。
        float: left
      .canvas
        flex-shrink: 0
        background: #fff
        box-shadow: rgba(33, 35, 38, 0.2) 0px 10px 10px -10px
      .zoomBar
         position: absolute
         display: flex
         bottom: 30px
         right: 30px
      .text-editor-bar
         position: absolute
         top: 30px
.side
    top:60px
    position: fixed
    &__menu
      // background: #252627 !important
      padding: 10px
      height: 100vh
      width: 120px
.icon
  &--crown
    color: #FFD039
</style>
