import {
  createRouter,createWebHistory,
  createWebHashHistory,
  RouteRecordRaw,
  RouterView,
} from "vue-router";
import HelloWorld from "../pages/HelloWorld.vue";
import Login from "../pages/Login.vue";
import register from "../pages/register3.vue";
const routes: RouteRecordRaw[] = [
  {
    path: "/",
    component: HelloWorld,
  },
  {
    path: "/login",
    name: "Login page",
    component: Login,
  },
  {
    path: "/signin",
    name: "signin",
    component: register,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes: routes,
});

export default router;