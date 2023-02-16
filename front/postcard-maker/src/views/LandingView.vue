<template>
  <div class="container">
    <!-- 网站名 -->
    <a-row class="sitename-container">
      <a-col>
        <h1 class="site-name">
          POST<span class="site-name--specialchar">C</span>ARD
          <span class="site-name--underlined"
            >M<span class="site-name--specialchar">A</span>KER</span
          >
        </h1>
      </a-col>
    </a-row>
    <!-- 表单卡片 -->
    <a-row class="form-container" type="flex" justify="center" align="middle">
      <a-col class="form-container__col" :xs="20" :sm="16" :md="12" :lg="8">
        <a-card class="form-card">
          <div class="form-card__msg">
            <div v-if="view == 0" class="form-card__title">USER LOGIN</div>
            <div v-if="view == 1" class="form-card__title">USER REGISTER</div>
          </div>
          <!-- 登录表单 -->
          <a-form
            class="form-card__form"
            v-if="view == 0"
            :model="loginForm"
            @finish="onLoginFormFinished"
          >
            <a-form-item
              name="email"
              validateTrigger="change"
              htm
              :rules="[
                {
                  required: true,
                  type: 'email',
                  message: '请输入合法邮箱',
                },
              ]"
            >
              <a-input
                class="form-card__input"
                v-model:value="loginForm.email"
                placeholder="电子邮箱"
              >
                <template #prefix> <mail-outlined type="user" /> </template
              ></a-input>
            </a-form-item>
            <a-form-item
              name="password"
              :rules="[
                {
                  required: true,
                  max: 16,
                  min: 6,
                  message: '密码应为6到16位',
                },
              ]"
            >
              <a-input-password
                class="form-card__input"
                v-model:value="loginForm.password"
                placeholder="密码"
              >
                <template #prefix> <lock-outlined type="user" /> </template
              ></a-input-password>
            </a-form-item>
            <a-form-item>
              <a-button class="form-card__btn" type="primary" html-type="submit"
                >登录</a-button
              >
              <a-button
                class="form-card__btn--nonprimary"
                type="dashed"
                @click="view = 1"
                >去注册</a-button
              >
            </a-form-item>
          </a-form>
          <!-- 登录表单结束 -->
          <!-- 注册表单 -->
          <a-form
            v-if="view == 1"
            class="form-card__form"
            :model="registerForm"
            @finish="onRegiFormFinished"
          >
            <a-form-item
              name="username"
              validateTrigger="change"
              htm
              :rules="[
                {
                  required: true,
                  pattern: new RegExp(/^[a-zA-Z][a-zA-Z0-9]{2,9}$/),
                  message: '3-10位，字母开头，仅包含字母数字',
                },
              ]"
            >
              <a-input
                v-model:value="registerForm.username"
                placeholder="用户名"
              />
            </a-form-item>
            <a-form-item
              name="email"
              validateTrigger="change"
              htm
              :rules="[
                {
                  required: true,
                  type: 'email',
                  message: '请输入合法邮箱',
                },
              ]"
            >
              <a-input
                v-model:value="registerForm.email"
                placeholder="电子邮箱"
              />
            </a-form-item>
            <a-form-item
              name="password"
              :rules="[
                {
                  required: true,
                  max: 16,
                  min: 6,
                  message: '密码应为6到16位',
                },
              ]"
            >
              <a-input-password
                v-model:value="registerForm.password"
                placeholder="密码"
              />
            </a-form-item>
            <a-form-item
              name="password2"
              :rules="[
                {
                  required: true,
                  max: 16,
                  min: 6,
                  message: '确认密码应为6到16位',
                },
              ]"
            >
              <a-input-password
                v-model:value="registerForm.password2"
                placeholder="确认密码"
              />
            </a-form-item>
            <a-form-item>
              <a-button class="form-card__btn" type="primary" html-type="submit"
                >注册</a-button
              >
              <a-button
                class="form-card__btn--nonprimary"
                type="dashed"
                @click="view = 0"
                >返回登录</a-button
              >
            </a-form-item>
          </a-form>
          <!-- 注册表单结束 -->
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>
<script>
import { encrypt } from "../utils/jsencrypt";
import { register } from "@/service/user";
import { login } from "@/service/user";
import { MailOutlined, LockOutlined } from "@ant-design/icons-vue";
import { message } from "ant-design-vue";
export default {
  components: {
    MailOutlined,
    LockOutlined,
  },
  watch: {
    // 切换登录注册界面时，清空输入框里的数据
    view(value) {
      if (value == 1) {
        this.registerForm.email = "";
        this.registerForm.password = "";
        this.registerForm.password2 = "";
        this.registerForm.username = "";
      } else {
        this.loginForm.email = "";
        this.loginForm.password = "";
      }
    },
  },
  data() {
    return {
      loginForm: {
        email: "",
        password: "",
      },
      registerForm: {
        email: "",
        username: "",
        password: "",
        password2: "",
      },
      view: 0, // 0: login , 1:register
    };
  },
  methods: {
    async handleLoginButtonClicked() {
      const res = await login({
        email: this.loginForm.email,
        password: encrypt(this.loginForm.password),
      });
      if (res.code == 200) {
        // todo 解密token提取数据
        const loginUser = {
          username: "patty",
          email: "",
        };
        const token = res.data.token;
        const refreshToken = res.data.refreshToken;
        this.$store.dispatch("login", { loginUser, token, refreshToken });
        this.$router.push("/home");
      } else {
        message.warn(res.message);
      }
    },
    async handleRegisterButtonClicked() {
      const user = {
        username: this.registerForm.username,
        password: this.registerForm.password,
        email: this.registerForm.email,
      };
      const res = await register(user);
      if (res.code == 200) {
        message.success("注册成功，请登录");
        // 转到登录试图
        this.view = 0;
      }
    },
    onLoginFormFinished() {
      this.handleLoginButtonClicked();
    },
    onRegiFormFinished() {
      this.handleRegisterButtonClicked();
    },
  },
};
</script>
<style lang="sass" scoped>

.container
  width: 100vw
  height: 100vh
  background-image: linear-gradient(to top, #fbc2eb 0%, #a6c1ee 100%)
  display: flex
  flex-direction: column
.sitename-container
  height: max-content
.site-name
  margin: 20px
  color: #fff
  &--specialchar
    color: #F1C2EC
  &--underlined
    text-decoration: underline
    text-underline-offset: 4px
    text-decoration-color: #C2C2EE

.form-container
  height:100%
  &__col
    max-width: 400px
.form-card
  padding: 10px 0
  background: #fff
  border: none
  border-radius: 20px
  &__msg
    text-align: center
    padding: 20px
    margin-bottom: 40px
  &__title
    color: #4E5255
    font-size: 30px
  &__message
    margin-top: 5px
    color: #7D8186
  &__form
    padding: 15px
    text-align: center
  &__input
    height: 50px
    &:not(first-of-type)
      margin-top: 10px
  &__btn
    margin-top: 20px
    border-radius: 10px
    width: 100px
    &--nonprimary
      margin-left: 30px
      border-radius: 10px
</style>
