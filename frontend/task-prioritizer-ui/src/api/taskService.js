import axios from "axios";

const API = axios.create({ baseURL: import.meta.env.VITE_API_BASE_URL });

export const getTasks = (label) =>
  label ? API.get(`/tasks?label=${label}`) : API.get("/tasks");

export const createTask = (task) => API.post("/tasks", task);
export const updateTask = (id, task) => API.put(`/tasks/${id}`, task);
export const deleteTask = (id) => API.delete(`/tasks/${id}`);
