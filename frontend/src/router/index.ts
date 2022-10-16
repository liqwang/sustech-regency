import {
  createRouter, createWebHashHistory,
  RouteRecordRaw,
} from 'vue-router';
import LoginPage from '../pages/LoginPage.vue';
import ChangePassword from '../pages/ChangePassword.vue';
import register from '../pages/register3.vue';
import merchant from '../pages/merchant.vue'
import Front from '../pages/front/Front.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'login',
    component: LoginPage,
  },
  {
    path: '/merchant',
    name: 'merchant',
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
  {
    path: '/',
    name: 'front',
    component: Front
  }
];

const router = createRouter({
  history: createWebHashHistory(),
  routes: routes,
});

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  if (token || to.path === '/' || to.path === '/login' || to.path === '/signup' || to.path === '/changepassword') {
    next();
  } else {
    next('/login');
  }
});

export default router;