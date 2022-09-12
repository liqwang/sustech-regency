import { createRouter,createWebHistory, createWebHashHistory, RouteRecordRaw, RouterView } from "vue-router";


const routes = [
   
  {
    path: '',
    name: 'home',
    component: () => import("../pages/HelloWorld.vue")
  },
  {
    path: '/login',
    name : 'Login',
    component :()=>import ("../pages/login.vue")
  },
  {
    path: '/register',
    name : 'Test',
    component :()=>import ("../pages/register.vue")
  },
  {
    path: '/register2',
    name : 'Register2',
    component :()=>import ("../pages/register2.vue")
  },
  {
    path: '/register3',
    name : 'Register3',
    component :()=>import ("../pages/register3.vue")
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes,
})

export default router