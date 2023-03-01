import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import axios from "axios";

// mitt
import mitt from "mitt";
const emitter = mitt();
// context-menu
import "@imengyu/vue3-context-menu/lib/vue3-context-menu.css";
import ContextMenu from "@imengyu/vue3-context-menu";
// fabric.js
import { fabric } from "fabric";
// colorPicker
import Vue3ColorPicker from "vue3-colorpicker";
import "vue3-colorpicker/style.css";
import "@/assets/style/colorPickerOverride.sass";
// infinite loading
import InfiniteLoading from "v3-infinite-loading";
import "v3-infinite-loading/lib/style.css";
// ant-degign-vue components
import {
  ConfigProvider,
  Button,
  Card,
  Grid,
  Row,
  Col,
  Input,
  Form,
  FormItem,
  Layout,
  Menu,
  Avatar,
  Checkbox,
  Tabs,
  TabPane,
  Divider,
  Slider,
  Select,
  Message,
  Empty,
  Upload,
  Alert,
  Modal,
  Progress,
  Popconfirm,
} from "ant-design-vue";
import "ant-design-vue/lib/card/style";
import "ant-design-vue/lib/button/style";
import "ant-design-vue/lib/grid/style";
import "ant-design-vue/lib/row/style";
import "ant-design-vue/lib/col/style";
import "ant-design-vue/lib/input/style";
import "ant-design-vue/lib/form/style";
import "ant-design-vue/lib/layout/style";
import "ant-design-vue/lib/menu/style";
import "ant-design-vue/lib/avatar/style";
import "ant-design-vue/lib/checkbox/style";
import "ant-design-vue/lib/tabs/style";
import "ant-design-vue/lib/divider/style";
import "ant-design-vue/lib/slider/style";
import "ant-design-vue/lib/select/style";
import "ant-design-vue/lib/message/style";
import "ant-design-vue/lib/upload/style";
import "ant-design-vue/lib/alert/style";
import "ant-design-vue/lib/modal/style";
import "ant-design-vue/lib/progress/style";
import "ant-design-vue/lib/popconfirm/style";

axios.defaults.baseURL = "http://127.0.0.1:8081/";

// http request拦截器
axios.interceptors.request.use((config) => {
  // 发送请求前做的事情
  if (store.getters.token) {
    config.headers.token = store.getters.token;
    config.headers.refreshToken = store.getters.refreshToken;
  }
  return config;
});

// http response拦截器
axios.interceptors.response.use(
  async (res) => {
    return res;
  },
  async (err) => {
    const originalRequest = err.config;
    if (err.response.status == 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      const res = await axios.post("/tokens", {});
      const resp = res.data;
      // 获得新的token
      if (resp.code == 200) {
        const token = resp.data;
        store.dispatch("updateToken", token);
        originalRequest.headers.token = store.getters.token;
        // 如果重新获得token成功，则重新发送请求
        return axios(originalRequest);
      } else {
        // 获取新token失败，登录状态失效
        store.dispatch("logout", {});
        router.push("/");
      }
    }
    return Promise.reject(err.response.data);
  }
);
export default axios;

const app = createApp(App);
app.config.globalProperties.emitter = emitter;
app.config.globalProperties.fabric = fabric;
app
  .use(Alert)
  .use(Grid)
  .use(Button)
  .use(Card)
  .use(Row)
  .use(Col)
  .use(Form)
  .use(FormItem)
  .use(Input)
  .use(Layout)
  .use(Menu)
  .use(Avatar)
  .use(Checkbox)
  .use(Tabs)
  .use(TabPane)
  .use(Divider)
  .use(Slider)
  .use(Select)
  .use(Message)
  .use(Empty)
  .use(Upload)
  .use(ConfigProvider)
  .use(Modal)
  .use(Progress)
  .use(Popconfirm);
app.use(Vue3ColorPicker);
app.use(ContextMenu);
app.component("InfiniteLoading", InfiniteLoading);
app.use(store).use(router).mount("#app");
