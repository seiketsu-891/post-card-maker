import { createRouter, createWebHistory } from "vue-router";
import LandingView from "../views/LandingView";

const routes = [
  {
    path: "/",
    name: "landingPage",
    component: LandingView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
