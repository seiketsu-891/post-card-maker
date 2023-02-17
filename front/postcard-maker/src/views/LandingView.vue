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
            <div v-if="view == 2" class="form-card__title">RESET PASSWORD</div>
          </div>
          <!-- 登录表单 -->
          <a-form
            class="form-card__form"
            v-if="view == 0"
            :model="loginForm"
            @finish="userLogin"
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
            <a class="form-card__forgotpwd" href="#" @click="view = 2"
              >找回密码</a
            >
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
            @finish="register"
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
            <!-- 注册-电子邮箱框-->
            <a-row>
              <a-col :span="16">
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
                  >
                  </a-input>
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-button type="default" @click="sendCode(1)">{{
                  codeBtn.text
                }}</a-button>
              </a-col>
            </a-row>
            <!-- 注册-验证码框 -->
            <a-form-item
              v-if="codeBtn.visibility == true && codeBtn.type == 1"
              name="code"
              validateTrigger="change"
              :rules="[
                {
                  required: true,
                  message: '请输入邮件验证码',
                },
              ]"
            >
              <a-input
                v-model:value="registerForm.code"
                placeholder="请输入邮件验证码"
              >
              </a-input>
            </a-form-item>
            <a-form-item
              name="password"
              :rules="[
                {
                  required: true,
                  message: '密码6-16位，不含空格',
                  pattern: new RegExp(/^[^\s]{6,16}$/),
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
                  validator: validatePassword2,
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
          <!-- 找回密码表单 -->
          <a-form
            class="form-card__form"
            @finish="resetPwd"
            v-if="view == 2"
            :model="resetPwdForm"
          >
            <!-- 找回密码-电子邮箱框-->
            <a-row>
              <a-col :span="16">
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
                    v-model:value="resetPwdForm.email"
                    placeholder="电子邮箱"
                  >
                  </a-input>
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-button type="default" @click="sendCode(2)">{{
                  codeBtn.text
                }}</a-button>
              </a-col>
            </a-row>
            <!-- 找回密码-验证码框 -->
            <a-form-item
              v-if="codeBtn.visibility == true && codeBtn.type == 2"
              name="code"
              validateTrigger="change"
              :rules="[
                {
                  required: true,
                  message: '请输入邮件验证码',
                },
              ]"
            >
              <a-input
                v-model:value="resetPwdForm.code"
                placeholder="请输入邮件验证码"
              >
              </a-input>
            </a-form-item>
            <a-form-item
              name="password"
              :rules="[
                {
                  required: true,
                  message: '密码6-16位，不含空格',
                  pattern: new RegExp(/^[^\s]{6,16}$/),
                },
              ]"
            >
              <a-input-password
                v-model:value="resetPwdForm.password"
                placeholder="密码"
              />
            </a-form-item>
            <a-form-item
              name="password2"
              :rules="[
                {
                  validator: validateRestPassword2,
                },
              ]"
            >
              <a-input-password
                v-model:value="resetPwdForm.password2"
                placeholder="确认密码"
              />
            </a-form-item>

            <a-form-item>
              <a-button class="form-card__btn" type="primary" html-type="submit"
                >重置密码</a-button
              >
              <a-button
                class="form-card__btn--nonprimary"
                type="dashed"
                @click="view = 0"
                >返回登录</a-button
              >
            </a-form-item>
          </a-form>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>
<script>
import jwt_decode from "jwt-decode";
import { encrypt } from "../utils/jsencrypt";
import { resetPassword } from "@/service/user";
import { register } from "@/service/user";
import { login } from "@/service/user";
import { sendCode } from "@/service/user";
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
      } else if (value == 0) {
        this.loginForm.email = "";
        this.loginForm.password = "";
      } else {
        this.resetPwdForm.email = "";
        this.resetPwdForm.code = "";
        this.resetPwdForm.password = "";
        this.resetPwdForm.password2 = "";
      }
      this.codeBtn.visibility = true;
      this.codeBtn.type = 0;
      this.codeBtn.text = "发送验证码";
      this.codeBtn.disabled = false;
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
        code: "",
      },
      resetPwdForm: {
        email: "",
        code: "",
        password: "",
      },
      view: 0, // 0: login , 1:register, 2: forgot password
      codeBtn: {
        visibility: false,
        type: 1, //  1: register 2. forgot password
        text: "发送验证码",
        disabled: false,
        timer: false,
      },
    };
  },
  methods: {
    async register() {
      // 如果用户还未发送验证码，则提示
      if (!this.codeBtn.visibility) {
        message.warn("您还未验证邮箱");
        return;
      }
      const res = await register({
        username: this.registerForm.username,
        email: this.registerForm.email,
        password: encrypt(this.registerForm.password),
        code: this.registerForm.code,
      });
      // 这里可加代码禁止表单重复提交
      // 练习重点内容期间这里先不做了
      if (res.code == 200) {
        message.success("注册成功,即将转入登录页面");
        setTimeout(() => {
          this.view = 0;
        }, 2000);
      } else {
        message.warn(res.message);
      }
    },
    /**
     * 重置密码
     */
    async resetPwd() {
      const res = await resetPassword({
        email: this.resetPwdForm.email,
        password: encrypt(this.resetPwdForm.password),
        code: this.resetPwdForm.code,
      });
      if (res.code == 200) {
        message.success("重置成功,即将转入登录页面");
        setTimeout(() => {
          this.view = 0;
        }, 2000);
      } else {
        message.warn(res.message);
      }
    },
    /**
     * 发送邮箱验证码
     */
    async sendCode(type) {
      const email =
        type == 1 ? this.registerForm.email : this.resetPwdForm.email;
      if (email.trim() == "") {
        message.warn("您还未输入邮箱");
        return;
      }
      if (this.codeBtn.disabled || this.codeBtn.timer) {
        return;
      }
      this.codeBtn.text = "发送中稍等";
      this.codeBtn.disabled = true;
      this.codeBtn.type = type;

      const purpose = type == 1 ? 0 : 1;
      const res = await sendCode(email, purpose);
      if (res.code != 200) {
        if (res.code == 0) {
          message.warn(res.message);
        } else {
          message.error("邮件发送失败");
        }
        this.codeBtn.text = "发送验证码";
        this.codeBtn.disabled = false;
        return;
      }
      message.success("邮件发送成功");
      this.codeBtn.visibility = true;
      this.codeBtn.timer = true;
      let s = 60;
      this.codeBtn.text = s + "秒后重试";
      const _this = this;
      const timer = setInterval(() => {
        s--;
        if (s <= 0) {
          _this.codeBtn.text = "发送验证码";
          this.codeBtn.disabled = false;
          this.codeBtn.timer = false;
          clearInterval(timer);
          // 启用按钮
        } else {
          this.codeBtn.text = s + "秒后重试";
        }
      }, 1000);
    },
    /**
     *  用于校验确认密码的规则
     */
    async validatePassword2(rule, value) {
      if (value === "") {
        return Promise.reject("请输入确认密码");
      } else if (value !== this.registerForm.password) {
        return Promise.reject("确认密码和密码不一致");
      } else {
        return Promise.resolve();
      }
    },
    async validateRestPassword2(rule, value) {
      if (value === "") {
        return Promise.reject("请输入确认密码");
      } else if (value !== this.resetPwdForm.password) {
        return Promise.reject("确认密码和密码不一致");
      } else {
        return Promise.resolve();
      }
    },
    /**
     * 处理登录
     */
    async userLogin() {
      const res = await login({
        email: this.loginForm.email,
        password: encrypt(this.loginForm.password),
      });
      if (res.code == 200) {
        const refreshToken = res.data.refreshToken;
        const token = res.data.token;
        const decodedToken = jwt_decode(token);
        const loginUser = {
          username: decodedToken.username,
          email: decodedToken.email,
        };
        this.$store.dispatch("login", { loginUser, token, refreshToken });
        this.$router.push("/home");
      } else {
        message.warn(res.message);
      }
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
  &__forgotpwd
    display: flex
    justify-content: right
</style>
