import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
// mitt
import mitt from "mitt";
const emitter = mitt();

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

const app = createApp(App);
app.config.globalProperties.emitter = emitter;
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
  .use(Divider);
app.use(Vue3ColorPicker);
app.use(store).use(router).mount("#app");
