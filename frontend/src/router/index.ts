import {
  createRouter,
  createWebHistory,
  RouteRecordRaw,
} from 'vue-router';
import LoginPage from '../pages/LoginPage.vue';
import ChangePassword from '../pages/ChangePassword.vue';
import register from '../pages/register3.vue';
import merchant from '../pages/merchant.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: LoginPage,
  },
  {
    path: '/merchant',
    name:'merchant',
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
  history: createWebHistory(),
  routes: routes,
});

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  if (token || to.path == '/' || to.path == '/signup' || to.path == '/changepassword'|| to.path=='/merchant') {
    next();
  } else {
    next('/');
  }
});

export default router;