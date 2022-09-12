import {
  createRouter,createWebHistory,
  createWebHashHistory,
  RouteRecordRaw,
  RouterView,
} from "vue-router";
import HelloWorld from "../pages/HelloWorld.vue";
import Login from "../pages/LoginPage.vue";
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
    path: "/signup",
    name: "signup",
    component: register,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes: routes,
});

export default router;