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
        <a-menu class="side__menu">
          <a-menu-item key="1">
            <template #icon>
              <CopyOutlined />
            </template>
            画布
          </a-menu-item>
          <a-menu-item key="2">
            <template #icon>
              <AppstoreOutlined />
            </template>
            素材
          </a-menu-item>
          <a-menu-item key="3">
            <template #icon>
              <FontSizeOutlined />
            </template>
            文字
          </a-menu-item>
          <a-menu-item key="4">
            <template #icon>
              <UploadOutlined />
            </template>
            素材
          </a-menu-item>
          <a-menu-item key="5" class="menu-item">
            <template #icon>
              <solutionOutlined />
            </template>
            我的
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <!-- 主要内容区 -->
      <a-layout-content class="main">main content</a-layout-content>
    </a-layout>
  </div>
</template>
<script>
import {
  FontSizeOutlined,
  UploadOutlined,
  SolutionOutlined,
  AppstoreOutlined,
  CopyOutlined,
  CrownFilled,
  CloudDownloadOutlined,
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
.side
    top:60px
    position: fixed
    &__menu
      padding: 10px
      height: 100vh
      width: 120px
.icon
  &--crown
    color: #FFD039
</style>
