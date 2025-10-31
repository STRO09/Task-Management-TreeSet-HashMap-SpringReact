// src/auth/AuthProvider.jsx
import { createContext, useContext, useState, useEffect } from "react";
import API from "../api/api";
import {jwtDecode} from "jwt-decode";

const AuthContext = createContext(null);

const TOKEN_KEY = "app_token_v1"; // localStorage key

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => {
    try {
      return localStorage.getItem(TOKEN_KEY);
    } catch (e) {
      return null;
    }
  });
  const [user, setUser] = useState(() => {
    if (!token) return null;
    try {
      return jwtDecode(token);
    } catch (e) {
      return null;
    }
  });

  // Set up axios interceptor to attach Authorization header
  useEffect(() => {
    const reqInterceptor = API.interceptors.request.use((config) => {
      if (token) {
        config.headers = config.headers || {};
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    });

    const resInterceptor = API.interceptors.response.use(
      (res) => res,
      (error) => {
        if (error.response && error.response.status === 401) {
          // optionally: logout automatically on 401
          clearAuth();
          // we don't redirect here; components can handle
        }
        return Promise.reject(error);
      }
    );

    return () => {
      API.interceptors.request.eject(reqInterceptor);
      API.interceptors.response.eject(resInterceptor);
    };
  }, [token]);

  function saveToken(newToken, remember = true) {
    setToken(newToken);
    try {
      if (remember) localStorage.setItem(TOKEN_KEY, newToken);
      else localStorage.removeItem(TOKEN_KEY); // keep in memory only
    } catch (e) {
      // ignore localStorage write errors
    }
    try {
      setUser(jwtDecode(newToken));
    } catch (e) {
      setUser(null);
    }
  }

  function clearAuth() {
    setToken(null);
    setUser(null);
    try {
      localStorage.removeItem(TOKEN_KEY);
    } catch (e) {}
  }

  const value = {
    token,
    user,
    isAuthenticated: !!token,
    setToken: saveToken,
    logout: clearAuth,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  return useContext(AuthContext);
}
