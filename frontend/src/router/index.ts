import { createRouter, createWebHistory } from 'vue-router'
import LoginPage from '@/views/LoginPage.vue'
import UserHomePage from '@/views/UserHomePage.vue'
import CursoHomePage from '@/views/CursoHomePage.vue'

const routes = [
  { path: '/', component: LoginPage },
  { path: '/home', component: UserHomePage, meta: { requiresAuth: true } },
  { path: '/cursos', component: CursoHomePage, meta: { requiresAuth: true } },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, from, next) => {
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)
  const token = localStorage.getItem('token')

  if (requiresAuth && !token) {
    next('/') // redireciona para login
  } else {
    next() // permite seguir
  }
})


export default router
