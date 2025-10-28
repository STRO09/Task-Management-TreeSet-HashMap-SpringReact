package repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	// Find all tasks belonging to a specific user
	List<Task> findByUserId(Long userId);

	// Find all tasks by user ordered by deadline (nulls last)
	List<Task> findByUserIdOrderByDeadlineAsc(Long userId);

	// Optional: filter by label (important, medium, less)
	List<Task> findByUserIdAndLabel(Long userId, String label);

	// Optional: find tasks due before a specific time (for reminders later)
	List<Task> findByUserIdAndDeadlineBefore(Long userId, LocalDateTime time);
}
