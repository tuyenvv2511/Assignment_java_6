import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import AdminLayout from '../layouts/AdminLayout.vue'
import DashboardView from '../views/DashboardView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/admin',
      component: AdminLayout,
      children: [
        {
          path: '',
          name: 'dashboard',
          component: DashboardView
        },
        // Placeholder for other routes
        {
          path: 'products',
          name: 'products',
          component: () => import('../views/product/ProductList.vue')
        },
        {
          path: 'products/add',
          name: 'product-add',
          component: () => import('../views/product/ProductForm.vue')
        },
        {
          path: 'products/edit/:id',
          name: 'product-edit',
          component: () => import('../views/product/ProductForm.vue')
        },
        {
          path: 'invoices',
          name: 'invoices',
          component: () => import('../views/invoice/InvoiceList.vue')
        },
        {
          path: 'invoices/:id',
          name: 'invoice-detail',
          component: () => import('../views/invoice/InvoiceDetail.vue')
        },
        {
          path: 'customers',
          name: 'customers',
          component: () => import('../views/customer/CustomerList.vue')
        },
        {
          path: 'customers/create',
          name: 'customer-create',
          component: () => import('../views/customer/CustomerForm.vue')
        },
        {
          path: 'customers/edit/:id',
          name: 'customer-edit',
          component: () => import('../views/customer/CustomerForm.vue')
        },
        {
          path: 'employees',
          name: 'employees',
          component: () => import('../views/employee/EmployeeList.vue')
        },
        {
          path: 'employees/create',
          name: 'employee-create',
          component: () => import('../views/employee/EmployeeForm.vue')
        },
        {
          path: 'employees/edit/:id',
          name: 'employee-edit',
          component: () => import('../views/employee/EmployeeForm.vue')
        }
      ]
    }
  ],
})

router.beforeEach((to, from, next) => {
  const publicPages = ['/login']
  const authRequired = !publicPages.includes(to.path)
  const loggedIn = localStorage.getItem('token')

  if (authRequired && !loggedIn) {
    next('/login')
  } else {
    next()
  }
})

export default router
