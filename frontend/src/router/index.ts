import { createRouter, createWebHashHistory, RouteRecordRaw, RouterView } from "vue-router";
import HelloWorld from "../pages/HelloWorld.vue"

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: HelloWorld,
    children: [
      {
        path: '',
        name: 'home',
        component: () => import("../pages/HelloWorld.vue")
      },
    ]
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes: routes
})

export default router