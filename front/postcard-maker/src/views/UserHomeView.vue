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
          <a-avatar class="header__avatar" style="background-color: #82c646"
            >K</a-avatar
          >
          <a-button class="header__btn--get-premium header__btn">
            <template #icon> <CrownFilled class="icon--crown" /> </template>
            成为会员</a-button
          >
          <a-button class="header__btn--download header__btn">
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
        <a-menu class="side__menu" v-model:selectedKeys="selectedMenuItemKey">
          <a-menu-item key="0" @click="handleSideMenuItemClicked(0)">
            <template #icon>
              <CopyOutlined />
            </template>
            画布
          </a-menu-item>
          <a-menu-item key="1" @click="handleSideMenuItemClicked(1)">
            <template #icon>
              <AppstoreOutlined />
            </template>
            素材
          </a-menu-item>
          <a-menu-item key="2" @click="handleSideMenuItemClicked(2)">
            <template #icon>
              <FontSizeOutlined />
            </template>
            文字
          </a-menu-item>
          <a-menu-item key="3" @click="handleSideMenuItemClicked(3)">
            <template #icon>
              <UploadOutlined />
            </template>
            上传
          </a-menu-item>
          <a-menu-item
            key="4"
            class="menu-item"
            @click="handleSideMenuItemClicked(4)"
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
            <!-- 文字框选择 -->
            <TextBoxLib v-show="activeMenu == menus[2]" />
            <!-- 自定义上传素材 -->
            <CustomResourceLib v-show="activeMenu == menus[3]" />
            <!-- 已保存的文件 -->
            <MyPostcards v-show="activeMenu == menus[4]" />
          </div>
          <!-- 折叠按钮 -->
          <button class="resource__collapsebtn" @click="collapeResourceArea">
            <LeftOutlined />
          </button>
        </div>
        <!-- 明信片编辑区 -->
        <div class="editor">
          <div class="canvas-container"><div class="canvas">canvas</div></div>
        </div></a-layout-content
      >
    </a-layout>
  </div>
</template>
<script>
import CanvasSetting from "@/components/CanvasSetting";
import ResourceLib from "@/components/ResourceLib";
import TextBoxLib from "@/components/TextBoxLib";
import CustomResourceLib from "@/components/CustomResourceLib";
import MyPostcards from "@/components/MyPostcards";
// 图标
import {
  FontSizeOutlined,
  UploadOutlined,
  SolutionOutlined,
  AppstoreOutlined,
  CopyOutlined,
  CrownFilled,
  CloudDownloadOutlined,
  LeftOutlined,
} from "@ant-design/icons-vue";
export default {
  components: {
    FontSizeOutlined,
    UploadOutlined,
    SolutionOutlined,
    AppstoreOutlined,
    CopyOutlined,
    CrownFilled,
    CloudDownloadOutlined,
    LeftOutlined,
    CanvasSetting,
    ResourceLib,
    TextBoxLib,
    CustomResourceLib,
    MyPostcards,
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
    };
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
      &__collapsebtn
        background-color: #fff
        // background-color: #343536
        position: absolute
        right: -30px
        top: 50%
        margin-top: -50px
        width: 30px
        height: 100px
        border: 0
        border-radius: 0 20px 20px 0
        text-align: center
        cursor: pointer
        &:hover
          color: #4661F4
    .editor
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
        width: 900px
        height: 900px
        background: #fff
        box-shadow: rgba(33, 35, 38, 0.2) 0px 10px 10px -10px
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
