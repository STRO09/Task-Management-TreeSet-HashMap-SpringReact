// src/pages/Login.jsx
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../api/auth";
import { useAuth } from "../auth/AuthProvider";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const [error, setError] = useState(null);
  const auth = useAuth();
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();
    setError(null);
    try {
      const res = await loginUser(username, password);
      const token = res.data.token;
      if (!token) throw new Error("No token in response");
      auth.setToken(token, true); // remember in localStorage
      navigate("/dashboard");
    } catch (err) {
      setError(err.response?.data?.message || err.message || "Login failed");
    }
  }

  function handleOAuth(provider) {
    // Redirect to backend OAuth2 auth endpoint.
    // Replace URL with your backend OAuth endpoint.
    window.location.href = `${
      import.meta.env.VITE_API_BASE_URL || ""
    }/oauth2/authorize/${provider}`;
  }

  return (
    <div style={{ maxWidth: 480, margin: "2rem auto" }}>
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Username
          <input
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </label>
        <label>
          Password
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </label>
        <button type="submit">Login</button>
      </form>

      {error && <div style={{ color: "red" }}>{error}</div>}

      <hr />
      <button onClick={() => handleOAuth("google")}>Login with Google</button>
      <button onClick={() => handleOAuth("github")}>Login with GitHub</button>
    </div>
  );
}
