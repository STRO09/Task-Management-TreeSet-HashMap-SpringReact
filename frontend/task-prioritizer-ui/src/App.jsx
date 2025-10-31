import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./auth/AuthProvider";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Taskboard from "./pages/TaskBoard";
import OAuthCallback from "./pages/OAuthCallback"
import ProtectedRoute from "./auth/ProtectedRoute";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/oauth/callback" element={<OAuthCallback />} />
          <Route
            path="/dashboard"
            element={
              <ProtectedRoute>
                <Taskboard />
              </ProtectedRoute>
            }
          />
          <Route path="/" element={<div>Home - <a href="/dashboard">Dashboard</a></div>} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
