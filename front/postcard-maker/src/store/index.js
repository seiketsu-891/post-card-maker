import { createStore } from "vuex";
import VuexPersistence from "vuex-persist";

const vuexLocal = new VuexPersistence({
  storage: window.localStorage,
});

export default createStore({
  plugins: [vuexLocal.plugin],
  state() {
    return {
      loginUser: null,
      token: null,
      refreshToken: null,
    };
  },
  getters: {
    // 这个是用于路由判定,用于获取登录状态
    logined(state) {
      return (
        state.loginUser !== null &&
        state.token !== null &&
        state.refreshToken != null
      );
    },
    user(state) {
      return state.loginUser;
    },
    token(state) {
      return state.token;
    },
    refreshToken(state) {
      return state.refreshToken;
    },
  },
  mutations: {
    login(state, { loginUser, token, refreshToken }) {
      state.loginUser = loginUser;
      state.token = token;
      state.refreshToken = refreshToken;
    },
    updateUser(state, user) {
      state.loginUser = user;
    },
    clear(state) {
      state.loginUser = null;
      state.token = null;
      state.refreshToken = null;
    },
    updateToken(state, token) {
      state.token = token;
    },
  },
  actions: {
    login(context, { loginUser, token, refreshToken }) {
      context.commit("login", { loginUser, token, refreshToken });
    },
    updateUser(context, user) {
      context.commit("updateUser", user);
    },
    updateToken(context, token) {
      context.commit("updateToken", token);
    },
    logout(context) {
      context.commit("clear");
    },
  },
  modules: {},
});
