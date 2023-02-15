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
            <div class="form-card__title">USER LOGIN</div>
            <!-- <div class="form-card__message">
              <p>Hey,来体验一下网站功能吧，你可以在本网站通过素材拼接生成</p>
            </div> -->
          </div>
          <!-- 登录表单 -->
          <a-form
            class="form-card__form"
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
            </a-form-item>
          </a-form>
          <!-- 登录表单结束 -->
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>
<script>
import { encrypt } from "../utils/jsencrypt";

import { login } from "@/service/user";
import { MailOutlined, LockOutlined } from "@ant-design/icons-vue";
import { message } from "ant-design-vue";
export default {
  components: {
    MailOutlined,
    LockOutlined,
  },
  data() {
    return {
      loginForm: {
        email: "",
        password: "",
      },
    };
  },
  methods: {
    async handleLoginButtonClicked() {
      const res = await login({
        email: this.loginForm.email,
        password: encrypt(this.loginForm.password),
      });
      if (res.code == 200) {
        // const token = res.data;
      } else {
        message.warn(res.message);
      }
    },
    onLoginFormFinished() {
      this.handleLoginButtonClicked();
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
  padding: 30px 0
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
    margin-top: 10px
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
</style>
