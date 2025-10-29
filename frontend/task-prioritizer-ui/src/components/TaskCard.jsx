import { Card, CardContent, Typography, Chip, IconButton } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import dayjs from "dayjs";

export default function TaskCard({ task, onEdit, onDelete }) {
  return (
    <Card sx={{ mb: 2 }}>
      <CardContent>
        <Typography variant="h6">{task.title}</Typography>
        {task.label && <Chip label={task.label} sx={{ mr: 1 }} />}
        {task.deadline && (
          <Typography variant="body2" color="text.secondary">
            Due: {dayjs(task.deadline).format("MMM D, YYYY h:mm A")}
          </Typography>
        )}
      </CardContent>
      <IconButton onClick={() => onEdit(task)}><EditIcon /></IconButton>
      <IconButton onClick={() => onDelete(task.id)}><DeleteIcon /></IconButton>
    </Card>
  );
}
