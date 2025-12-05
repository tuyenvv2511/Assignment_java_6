import axios from 'axios'

const request = axios.create({
    baseURL: 'http://localhost:8080/api', // Adjust if backend port is different
    timeout: 5000
})

// Request interceptor
request.interceptors.request.use(
    config => {
        const token = localStorage.getItem('token')
        if (token) {
            // Token already contains 'Basic ' prefix from login
            config.headers['Authorization'] = token
        }
        return config
    },
    error => {
        console.log(error)
        return Promise.reject(error)
    }
)

// Response interceptor
request.interceptors.response.use(
    response => {
        return response.data
    },
    error => {
        console.log('err' + error)
        return Promise.reject(error)
    }
)

export default request
