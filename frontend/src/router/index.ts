import {
  createRouter,
  createWebHashHistory,
  RouteRecordRaw,
  RouterView,
} from "vue-router";
import HelloWorld from "../pages/HelloWorld.vue";
import Login from "../pages/Login.vue";

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
];

const router = createRouter({
  history: createWebHashHistory(),
  routes: routes,
});

export default router;
