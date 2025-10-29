import { useEffect, useState } from "react";
import { getTasks, deleteTask } from "../api/taskService";
import TaskCard from "../components/TaskCard";
import { Container, Typography } from "@mui/material";

export default function TaskBoard() {
  const [tasks, setTasks] = useState([]);

  const fetchTasks = async () => {
    const res = await getTasks();
    setTasks(res.data);
  };

  useEffect(() => {
    fetchTasks();
  }, []);

  const handleDelete = async (id) => {
    await deleteTask(id);
    fetchTasks();
  };

  return (
    <Container sx={{ mt: 4 }}>
      <Typography variant="h4" gutterBottom>
        My Tasks
      </Typography>
      {tasks.map((task) => (
        <TaskCard key={task.id} task={task} onDelete={handleDelete} />
      ))}
    </Container>
  );
}
