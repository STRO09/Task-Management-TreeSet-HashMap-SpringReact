package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	// Find all tasks belonging to a specific user
	List<Task> findByCreatorId(Long creatorId);


	// Optional: filter by label (important, medium, less)
	List<Task> findByCreatorIdAndLabelPriority(Long creatorId, String label);
	
	void deleteByCreatorId(Long creatorId);

}
