import { createRouter, createWebHistory } from 'vue-router'

// import rutas/vistas
import HomeView from '../views/HomeView.vue'
import ConflictView from '../views/ConflictView.vue'
import CreateView from '../views/CreateView.vue'
import NotFound from '../views/NotFound.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/conflicts',
    name: 'conflicts',
    component: HomeView
  },
  {
    path: '/conflicts/:id',
    name: 'conflictDetail',
    component: ConflictView,
    props: true
  },
  
  {
    path: '/conflicts/:id/edit',
    name: 'conflictEdit',
    component: () => import('../views/EditView.vue'),
    props: true
  },
  {
    path: '/create',
    name: 'createConflict',
    component: CreateView
  },
  
  {
    path: '/:pathMatch(.*)*',
    name: 'notfound',
    component: NotFound
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router