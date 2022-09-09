import { createApp } from 'vue'
import App from './App.vue'
import elementPlus from './plugins/element-plus'
import router from './router'
import "./styles/reset.css"

createApp(App)
  .use(elementPlus, { size: "mini" })
  .use(router)
  .mount('#app')