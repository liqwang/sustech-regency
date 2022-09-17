import {
  createRouter,
  createWebHistory,
  createWebHashHistory,
  RouteRecordRaw,
  RouterView,
} from 'vue-router';
import HelloWorld from '../pages/HelloWorld.vue';
import LoginPage from '../pages/LoginPage.vue';
import ChangePassword from '../pages/ChangePassword.vue';
import register from '../pages/register3.vue';
import merchant from '../pages/merchant.vue'
import FrontLayout from '../pages/back/BackLayout.vue';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: LoginPage,
  },
  {
    path: '/merchant',
    component: merchant,
  },
  {
    path: '/signup',
    name: 'signup',
    component: register,
  },
  {
    path: '/changepassword',
    name: 'changepassword',
    component: ChangePassword,
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes: routes,
});

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  console.log(to.path);
  if (token || to.path == '/' || to.path == '/signup' || to.path == '/changepassword'|| to.path=='/merchant') {
    next();
  } else {
    next('/');
  }
});

export default router;