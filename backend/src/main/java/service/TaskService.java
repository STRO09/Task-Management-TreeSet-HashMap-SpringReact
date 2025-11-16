package service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Task;
import repository.TaskRepository;

@Service
public class TaskService {
	private final TaskRepository repo;
	private final TaskCollectionManager cache;

	@Autowired
	public TaskService(TaskRepository repo, TaskCollectionManager cache) {
		this.repo = repo;
		this.cache = cache;
	}

	public void initializeCacheForUser(Long creatorId) {
		List<Task> tasks = repo.findByCreatorId(creatorId);
		tasks.forEach(cache::addTask);
	}

	public List<Task> getAllTasks() {
		return new ArrayList<>(cache.getAllTasks());
	}

	public Task createTask(Task task) {
		Task saved = repo.save(task);
		cache.addTask(saved);
		return saved;
	}

	public void deleteTask(Long id) {
		repo.deleteById(id);
		cache.removeTask(id);
	}

	public Task updateTask(Long id, Task updatedTask) {
		// Ensure the ID matches
		updatedTask.setId(id);
		Task saved = repo.save(updatedTask);
		cache.updateTask(saved);
		return saved;
	}

	public List<Task> getTasksByLabel(String label) {
		return cache.getAllTasks().stream()
				.filter(t -> t.getLabel() != null && t.getLabel().toString().equalsIgnoreCase(label))
				.collect(Collectors.toList());
	}
	
	public void deleteAllTasksForUser(Long userId) {
		// 1️. Delete from DB
		repo.deleteByCreatorId(userId);

		// 2. Remove from cache
		List<Long> taskIds = cache.getAllTasks().stream()
				.filter(t -> t.getCreator().getId().equals(userId))
				.map(Task::getId)
				.collect(Collectors.toList());

		taskIds.forEach(cache::removeTask);
	}
}
