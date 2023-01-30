import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
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

const app = createApp(App);
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
  .use(TabPane);
app.use(Vue3ColorPicker);
app.use(store).use(router).mount("#app");
