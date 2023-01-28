import { createRouter, createWebHistory } from "vue-router";
import LandingView from "../views/LandingView";
import UserHomeView from "../views/UserHomeView";

const routes = [
  {
    path: "/",
    name: "landingPage",
    component: LandingView,
  },
  {
    path: "/home",
    name: "UserHome",
    component: UserHomeView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
