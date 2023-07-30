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
          <a-button
            class="header__btn header__btn--new-postcard"
            @click="HandleNewPostcardBtnCliked"
            >新建明信片</a-button
          >
          <!--下面加入判断是避免退出登录时控制台报错  -->
          <a-avatar class="header__avatar" style="background-color: #82c646">
            {{
              user && user.username
                ? user.username.substring(0, 1).toUpperCase()
                : "A"
            }}</a-avatar
          >
          <a-button
            @click="openTaskModal"
            class="header__btn--get-premium header__btn"
          >
            <template #icon> <CrownFilled class="icon--crown" /> </template>
            <span v-if="isPremium">您是会员</span
            ><span v-else>成为会员</span></a-button
          >
          <a-modal v-model:visible="taskModalVisible" :title="taskModalTitle">
            <template #footer>
              <a-button key="back" @click="taskModalVisible = false"
                >返回</a-button
              >
              <a-button
                v-if="tasks.allCompleted && !isPremium"
                key="submit"
                type="primary"
                :loading="loading"
                @click="becomePremium"
                >点击成为会员</a-button
              >
            </template>
            <a-alert
              v-if="tasks.allCompleted && !isPremium"
              style="margin-bottom: 30px"
              message="您已完成所有任务，点击下方按钮即可成为会员"
              type="info"
            />
            <div
              class="taskmodal__container"
              v-for="(t, index) in tasks.list"
              :key="t.id"
            >
              <div class="taskmodal__wrapper">
                <p class="taskmodal__desc">{{ t.description }}</p>
                <span>
                  <!-- 任务没完成的显示  -->
                  <a-button v-if="!t.completed" @click="goToTask(t.code)">{{
                    "去完成"
                  }}</a-button>
                  <!-- 任务已完成的显示 -->
                  <span v-else>
                    <a-progress type="circle" :percent="100" :width="30"
                  /></span>
                </span>
              </div>
              <a-divider v-if="index != tasks.list.length - 1" a-divider />
            </div>
          </a-modal>
          <a-popconfirm
            placement="bottom"
            ok-text="我知道了"
            @confirm="downloadPopNoticeVisible = false"
            :showCancel="false"
            :visible="downloadPopNoticeVisible"
          >
            <template #title>
              <p>点击这里即可下载明信片</p>
            </template>
            <a-button
              @click="handleDownloadClicked()"
              class="header__btn--download header__btn"
            >
              <template #icon>
                <CloudDownloadOutlined class="icon--downloand" />
              </template>
              下载文件</a-button
            >
          </a-popconfirm>
          <a-button class="header__btn" @click="userLogout">登出</a-button>
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
        <div class="resource__box">
          <div class="resource">
            <div class="resource__container" v-show="resourceAreaVisible">
              <!-- 画布设置 -->
              <CanvasSetting v-show="activeMenu == menus[0]" />
              <!-- 素材选择 -->
              <ResourceLib v-show="activeMenu == menus[1]" />
              <!-- 形状选择 -->
              <ShapeLib v-show="activeMenu == menus[2]" />
              <!-- 文字框选择 -->
              <TextInsertion v-show="activeMenu == menus[3]" />
              <!-- 自定义上传素材 -->
              <CustomResourceLib v-if="activeMenu == menus[4]" />
              <!-- 已保存的文件 -->
              <MyPostcards v-if="activeMenu == menus[5]" />
            </div>
            <!-- 折叠按钮 -->
            <button class="resource__collapsebtn" @click="toggleResourceArea">
              <LeftOutlined v-show="resourceAreaVisible" />
              <RightOutlined v-show="!resourceAreaVisible" />
            </button>
          </div>
        </div>
        <!-- 明信片编辑区 -->
        <div class="editor" @wheel.prevent="onWheelScrolledInEditor($event)">
          <!-- 自动存储提示区域 -->
          <div class="status">
            <div class="redo-undo-buttons">
              <a-button
                type="link"
                @click="HandleUndoOrRedoClicked('undo')"
                :disabled="undoDisabled"
                >撤销</a-button
              >
              <a-button
                type="link"
                @click="HandleUndoOrRedoClicked('redo')"
                :disabled="redoDisabled"
                >重做</a-button
              >
            </div>
            <div class="auto-saving-status">
              <span class="auto-saving-status__msg">{{ autoSavingMsg }}</span>
              <a-spin class="auto-saving-status__spin" v-if="isSavingCanvas">
                <LoadingOutlined spin />
              </a-spin>
              <span class="auto-saving-status__ok" v-else>
                <CheckOutlined />
              </span>
            </div>
          </div>
          <EleEditorBar class="ele-editor-bar" />
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
import { ifPremium, logout, becomePremium } from "@/service/user";
import { getTasks, completeTask } from "@/service/tasks";
import CanvasSetting from "@/components/CanvasSetting";
import ResourceLib from "@/components/ResourceLib";
import ShapeLib from "@/components/ShapeLib";
import TextInsertion from "@/components/TextInsertion";
import CustomResourceLib from "@/components/CustomResourceLib";
import MyPostcards from "@/components/MyPostcards";
import CanvasBox from "@/components/editor/CanvasBox";
import ZoomBar from "@/components/editor/ZoomBar";
import EleEditorBar from "@/components/editor/EleEditorBar";
// 图标
import {
  CheckOutlined,
  FontSizeOutlined,
  UploadOutlined,
  SolutionOutlined,
  StarOutlined,
  CopyOutlined,
  CrownFilled,
  CloudDownloadOutlined,
  LeftOutlined,
  RightOutlined,
  FileImageOutlined,
  LoadingOutlined,
} from "@ant-design/icons-vue";
import { message } from "ant-design-vue";
export default {
  components: {
    CheckOutlined,
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
    RightOutlined,
    CanvasSetting,
    ResourceLib,
    CustomResourceLib,
    MyPostcards,
    CanvasBox,
    ShapeLib,
    ZoomBar,
    EleEditorBar,
    LoadingOutlined,
  },
  data() {
    return {
      undoDisabled: false,
      redoDisabled: false,
      // todo 加上事件
      isSavingCanvas: false,
      TASK_ID_DOWNLOAD_POSTCARD: 2,
      downloadPopNoticeVisible: false,
      taskModalVisible: false,
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
      isPremium: false,
      tasks: [],
    };
  },
  computed: {
    user() {
      return this.$store.getters.user;
    },
    taskModalTitle() {
      return this.isPremium ? "您已完成任务并获得会员" : "完成任务即可获取会员";
    },
    autoSavingMsg() {
      return this.isSavingCanvas ? "保存中" : "已保存";
    },
  },
  created() {
    this.checkIfPremium();
  },
  mounted() {
    this.emitter.on("changeUndoStatus", (args) => {
      this.undoDisabled = args.status;
    });
    this.emitter.on("changeRedoStatus", (args) => {
      this.redoDisabled = args.status;
    });
    // 监听画布自动保存状态
    this.emitter.on("canvas-saving-start", () => {
      this.isSavingCanvas = true;
    });
    this.emitter.on("canvas-saving-ok", () => {
      this.isSavingCanvas = false;
    });
  },
  methods: {
    async becomePremium() {
      const res = await becomePremium();
      if (res.code == 200) {
        message.success({
          content: "您已成为会员",
          duration: 2,
        });
        this.taskModalVisible = false;
        this.checkIfPremium();
      } else {
        message.error(res.message);
      }
    },
    /**
     * 今日任务提示界面
     */
    goToTask(code) {
      if ("T_UPLOAD_IMAGE" == code) {
        // 提示上传图片
        this.taskModalVisible = false;
        this.activeMenu = this.menus[4];
        this.resourceAreaVisible = true;
        this.selectedMenuItemKey = ["4"];
        // 如果一开始就处在自定义图片界面，emit过后会立即起效果
        // 否则需要用$nextTick
        this.$nextTick(() => {
          this.emitter.emit("customImgshowPop");
        });
        this.emitter.emit("customImgshowPop");
      } else if ("T_DOWNLOAD_POSTCARD" == code) {
        // 提示下载按钮
        this.taskModalVisible = false;
        this.downloadPopNoticeVisible = true;
      } else {
        return;
      }
    },
    hideAllPosNotice() {
      this.downloadPopNoticeVisible = false;
      this.emitter.emit("HidePopNotice");
    },
    /**
     * 打开成为会员任务的模态框
     */
    openTaskModal() {
      // 打开modal
      this.hideAllPosNotice();
      this.taskModalVisible = true;
      this.getTaskInfos();
    },
    async getTaskInfos() {
      const res = await getTasks();
      if (res.code == 200) {
        this.tasks = res.data;
      } else {
        message.error(res.message);
      }
    },
    /**
     * 退出登录
     */
    async userLogout() {
      const res = await logout();
      if (res.code == 200) {
        this.$store.dispatch("logout");
        this.$router.push("/");
      } else {
        message.error("操作失败");
      }
    },
    /**
     * 检查用户是否是会员
     */
    async checkIfPremium() {
      const res = await ifPremium();
      if (res.code == 200) {
        this.isPremium = res.data;
      } else {
        message.error(res.message);
      }
    },
    /**
     * 隐藏/显示侧边可折叠菜单区
     */
    toggleResourceArea() {
      this.resourceAreaVisible = !this.resourceAreaVisible;
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
    async handleDownloadClicked() {
      this.emitter.emit("convertCanvasToImage", {});
      // 下载任务的处理
      if (!this.isPremium) {
        const res = await completeTask(this.TASK_ID_DOWNLOAD_POSTCARD);
        if (res.code != 200) {
          message.error(res.message);
        }
      }
    },
    /**
     * 处理点击新建按钮后的操作
     */
    HandleNewPostcardBtnCliked() {
      this.emitter.emit("initDefaultCanvas");
      this.emitter.emit("changeUndoStatus", { status: true });
      this.emitter.emit("changeUndoStatus", { status: true });
    },
    /**
     * 撤销或重做
     */
    HandleUndoOrRedoClicked(type) {
      this.emitter.emit("undoOrRedo", { type });
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
      margin-left: 20px
      padding: 0 20px
      color: #4A4A4A
      &--new-postcard
         margin-right: 20px
         height: 30px
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
.main
    margin-top: 60px
    margin-left: 120px
    height: calc(100vh - 60px)
    background-color:#EBECF0
    display: flex
    .resource
      flex-shrink: 0
      // 要让infinite scroll发挥作用的关键魔法！
      overflow-y: auto
      overflow-x: visible
      z-index: 999
      height: 100%
      background-color: #fff
      // background-color: #343536
      box-shadow: 25px 0 20px -20px rgba(101, 138, 216, 0.2)
      &__box
        position: relative
      &__container
        margin: 40px 25px
        width: 300px
        // 前面overflow-y: auto加了后，这里不可以 height: 100%
        // 不然会让scroll bar
        // height: 100%
      &__collapsebtn
        z-index: 999
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
      // padding: 80px
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
      .ele-editor-bar
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
.taskmodal
  &__wrapper
    display: flex
    justify-content: space-between
    align-items: center
  &__disc
    margin: 0
.status
  display: flex
  align-items: center
  color: #4A4A4A
  padding:5px 20px
  position: absolute
  background: rgba(255,255,255,.5)
  border-radius: 30px
  right: 10px
  top: 10px
  z-index: 999
  box-shadow: 20px 20px 21px -13px rgba(0,0,0,0.1)
.redo-undo-buttons
  color: #4A4A4A
.auto-saving-status
  height: min-content
  &__msg
    margin-right: 10px
  &__spin
    color: #5B8DDC
  &__ok
    color: #07B140
</style>
