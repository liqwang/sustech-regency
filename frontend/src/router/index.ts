import { createRouter, createWebHashHistory, RouteRecordRaw, RouterView } from 'vue-router'
import LoginPage from '../pages/LoginPage.vue'
import ChangePassword from '../pages/ChangePassword.vue'
import register from '../pages/register3.vue'
import merchant from '../pages/merchant.vue'
import Front from '../pages/front/Front.vue'
import chat from '../pages/chat.vue'
import HotelPage from '../pages/front/hotel/HotelPage.vue'
import UserPage from '../pages/front/user/UserPage.vue'
import HotelIntroduction from '../pages/front/hotel/HotelIntroduction.vue'
import HotelBookRoom from '../pages/front/hotel/HotelBookRoom.vue'
import HotelFloorPlan from '../pages/front/hotel/HotelFloorPlan.vue'
import HotelComment from '../pages/front/hotel/HotelComment.vue'
import UserSetting from '../pages/front/user/UserSetting.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'login',
    component: LoginPage,
    meta: { requiresAuth: false }
  },
  {
    path: '/merchant',
    name: 'merchant',
    component: merchant,
    meta: { requiresAuth: true }
  },
  {
    path: '/signup',
    name: 'signup',
    component: register,
    meta: { requiresAuth: false }
  },
  {
    path: '/changepassword',
    name: 'changepassword',
    component: ChangePassword,
    meta: { requiresAuth: false }
  },
  {
    path: '/chat',
    name: 'chat',
    component: chat,
    meta: { requiresAuth: false }
  },
  {
    path: '/',
    name: 'front',
    component: Front,
    meta: { requiresAuth: false }
  },
  {
    path: '/hotel/:hotelId(\\d+)',
    name: 'hotel',
    component: HotelPage,
    meta: { requiresAuth: false },
    children: [
      { path: '', name: 'introduction', component: HotelIntroduction, meta: { requiresAuth: false } },
      { path: 'bookroom', name: 'bookroom', component: HotelBookRoom, meta: { requiresAuth: false } },
      { path: 'floorplan', name: 'floorplan', component: HotelFloorPlan, meta: { requiresAuth: false } },
      { path: 'comment', name: 'comment', component: HotelComment, meta: { requiresAuth: false } }
    ]
  },
  {
    path: '/user',
    name: 'user',
    component: UserPage,
    meta: { requiresAuth: true },
    children: [
      { path: 'setting', name: 'setting', component: UserSetting }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes: routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.meta.requiresAuth && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
