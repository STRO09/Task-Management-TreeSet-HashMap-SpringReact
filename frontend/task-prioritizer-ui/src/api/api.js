// src/api/api.js
import axios from "axios";

const API = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "/",
  // withCredentials: true, // uncomment if backend uses cookies with CORS
});

// Request interceptor will be set at runtime from AuthProvider
export default API;
