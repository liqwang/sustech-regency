import {
  createRouter,createWebHistory,
  createWebHashHistory,
  RouteRecordRaw,
  RouterView,
} from "vue-router";
import HelloWorld from "../pages/HelloWorld.vue";
import LoginPage from "../pages/LoginPage.vue";
import register from "../pages/register3.vue";
const routes: RouteRecordRaw[] = [
  {
    path: "/",
    component: LoginPage,
  },
  // {
  //   path: "/login",
  //   name: "Login page",
  //   component: LoginPage,
  // },
  {
    path: "/signup",
    name: "signup",
    component: register,
  }
];

const router = createRouter({
  history: createWebHashHistory(),
  routes: routes,
});

export default router;