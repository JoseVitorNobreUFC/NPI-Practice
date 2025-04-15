import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { vuetify } from './plugins/vuetify'
import { loadFonts } from './plugins/webfoantloader'
const app = createApp(App)
import './styles/global.css'

loadFonts()

app.use(vuetify)
app.use(router)
app.mount('#app')
