package service;

import java.util.ArrayList;
import java.util.List;

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

    @PostConstruct
    private void initializeCache() {
        repo.findAll().forEach(cache::addTask);
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
}
