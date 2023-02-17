import { createRouter, createWebHistory } from "vue-router";
import LandingView from "../views/LandingView";
import UserHomeView from "../views/UserHomeView";
import store from "@/store";

const routes = [
  {
    path: "/",
    name: "landingPage",
    component: LandingView,
    meta: { loginUserVisible: false },
  },
  {
    path: "/home",
    name: "UserHome",
    component: UserHomeView,
    meta: {
      requireAuth: true,
    },
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  if (!to.matched.length) {
    window.location = "/404";
    return;
  }

  const isAuthenticated = store.getters.logined;
  if (
    to.matched.some((record) => record.meta.requireAuth) &&
    !isAuthenticated
  ) {
    if (to.path === "/home") {
      next({ path: "/" });
    }
  } else if (
    to.matched.some((record) => record.meta.loginUserVisible === false) &&
    isAuthenticated
  ) {
    // 已登录用户不可访问注册、登录、找回密码页面
    next({ path: "/home" });
  } else {
    next();
  }
});

export default router;
