import axios from 'axios';

// Axios örneği oluştur
const instance = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin':'http://localhost:5173'
  },
});

// İstek interceptor'ı ekle
instance.interceptors.request.use(
  (config) => {
    // Token'ı localStorage'dan al
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Yanıt interceptor'ı ekle (isteğe bağlı)
instance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default instance;