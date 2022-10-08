import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import router from './router'
import './styles/reset.css'
import './styles/index.css'
import 'element-plus/dist/index.css'

createApp(App)
  .use(ElementPlus, { size: "mini" })
  .use(router)
  .mount('#app')