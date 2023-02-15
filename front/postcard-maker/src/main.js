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
// ant-degign-vue components
import {
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

axios.defaults.baseURL = "http://127.0.0.1:8081/";

// http request拦截器
axios.interceptors.request.use((config) => {
  // 发送请求前做的事情
  if (store.getters.token) {
    config.headers.token = store.getters.token;
  }
  return config;
});

// http response拦截器
axios.interceptors.response.use(
  (res) => {
    return res;
  },
  async (err) => {
    const originalRequest = err.config;
    // 处理token过期情况
    if (err.response.status === 401 && !originalRequest._retry) {
      originalRequest.retry = true;
      const res = await axios.post("/tokens", {});
      const resp = res.data;
      // 获得新的token
      if (res.code === "200") {
        const token = resp.data;
        store.dispatch("updateToken", { token });
        originalRequest.headers.token = token;
        // 如果重新获得token成功，则重新发送请求
        return axios(originalRequest);
      } else {
        // 获取新token失败，登录状态失效
        store.dispatch("logout", {});
        router.push("/login");
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
  .use(Message);
app.use(Vue3ColorPicker);
app.use(ContextMenu);
app.use(store).use(router).mount("#app");
