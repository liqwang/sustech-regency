import {
  createRouter,createWebHistory,
  createWebHashHistory,
  RouteRecordRaw,
  RouterView,
} from "vue-router";
import h from "../pages/HelloWorld.vue";

import merchant from "../pages/merchant.vue";
import LoginPage from "../pages/LoginPage.vue";
import register from "../pages/register3.vue";
const routes: RouteRecordRaw[] = [
  {
    path: "/",
    component: LoginPage,
  },
  {
    path: "/merchant",
    name: "shangjia",
    component: merchant,
  },
  {
    path: "/signup",
    name: "signup",
    component: register,
  },
  {
    path: "/t",
    name: "s2",
    component: h,
  }
];

const router = createRouter({
  history: createWebHashHistory(),
  routes: routes,
});

router.beforeEach((to, from ,next) => {
  const token = localStorage.getItem("token");
  console.log(to.path)
  if (token || to.path == "/" || to.path == '/signup'|| to.path == '/merchant'|| to.path=='/t') {
    next();
  } else {
    next("/");
  }
})

export default router;