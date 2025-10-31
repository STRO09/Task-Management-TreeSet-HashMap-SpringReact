// src/pages/OAuthCallback.jsx
import React, { useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { useAuth } from "../auth/AuthProvider";

export default function OAuthCallback() {
  const navigate = useNavigate();
  const { search } = useLocation();
  const auth = useAuth();

  useEffect(() => {
    const params = new URLSearchParams(search);
    const token = params.get("token");
    const redirect = params.get("redirect") || "/dashboard";
    if (token) {
      auth.setToken(token, true);
      // remove token from URL for cleanliness
      navigate(redirect, { replace: true });
    } else {
      // no token → show message or redirect to login
      navigate("/login");
    }
  }, [search]);

  return <div>Logging you in...</div>;
}
