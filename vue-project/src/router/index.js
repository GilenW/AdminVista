import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import TodoList from '@/views/TodoList.vue'
import ElementPlusPractice from '@/views/ElementPlusPractice.vue'
import RouterVuePractice from '@/views/RouterVuePractice.vue'
import Manager from '@/views/Manager.vue'
import Analysis from '@/views/Analysis.vue'
import Employees from '@/views/Employees.vue'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [

    {
      path: '/',
      redirect: '/manager/home',
    },
    {
      path: '/manager',
      name: 'Manager Page',
      component: Manager,
      meta: {
        title: 'Manager Page',
      },
      children: [
        {
          path: 'home',
          name: 'home',
          component: TodoList,
          meta: {
            title: 'Home Page',
          },
        },
        {
          path: 'analysis',
          name: 'analysis',
          component: Analysis,
          meta: {
            title: 'Analysis Page',
          },
        },
        {
          path: 'employees',
          name: 'employees',
          component: Employees,
          meta: {
            title: 'Employees',
          },
        },

      ],
    },
    {
      path:'/login',
      name: 'Login Page',
      meta:{
        title: 'Login Page',
      },
      component: Login,
    },
    {
      path:'/register',
      name: 'Register Page',
      meta:{
        title: 'Register Page',
      },
      component: Register,
    },

    // the follwoing are practice routers
    // {
    //   path: '/',
    //   name: 'elementPlusPractice',
    //   component: ElementPlusPractice,
    //   meta: {
    //     title: 'elementPlusPractice',
    //   },
    // },
    {
      path: '/router-practice',
      name: 'router-practice',
      component: RouterVuePractice,
      children: [{ path: 'router-child1', name: 'router-child1', component: null }],
      meta: {
        title: 'router-practice',
      },
    },
    {
      path: '/404',
      name: 'Page not found',
      meta: { title: '404 page not found' },
      component: () => import('./404.vue'),
    },
    { path: '/:pathMatch(.*)', redirect: '/404' },
  ],
})

router.beforeEach((to, from, next) => {
  document.title = to.name
  next()
})
export default router
