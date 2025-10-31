import API from "./api";

export const loginUser = (username, password) =>
  API.post("/api/auth/login", { username, password });

export const registerUser = (username, password , email) =>
  API.post("/user", { username, password, email }); 
