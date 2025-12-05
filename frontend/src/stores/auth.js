import { defineStore } from 'pinia'
import request from '@/utils/request'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        token: localStorage.getItem('token') || '',
        user: JSON.parse(localStorage.getItem('user')) || null
    }),
    getters: {
        isAuthenticated: (state) => !!state.token,
        isAdmin: (state) => state.user?.role === 'ADMIN',
        isStaff: (state) => state.user?.role === 'STAFF',
        userName: (state) => state.user?.name || 'User',
        userRole: (state) => state.user?.role || 'Guest'
    },
    actions: {
        async login(username, password) {
            try {
                const response = await request.post('/auth/login', {
                    username,
                    password
                })

                this.token = response.token
                this.user = {
                    name: response.name,
                    role: response.role,
                    username: username
                }

                localStorage.setItem('token', this.token)
                localStorage.setItem('user', JSON.stringify(this.user))

                return true
            } catch (error) {
                console.error('Login failed:', error)
                throw error
            }
        },
        logout() {
            this.token = ''
            this.user = null
            localStorage.removeItem('token')
            localStorage.removeItem('user')
        }
    }
})
