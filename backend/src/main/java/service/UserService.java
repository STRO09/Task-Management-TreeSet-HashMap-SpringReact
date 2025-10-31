package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import model.User;
import repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final TaskService taskService;

    @Autowired
    public UserService(UserRepository repo, PasswordEncoder encoder, TaskService taskService) {
        this.repo = repo;
        this.encoder = encoder;
		this.taskService = taskService;
    }

    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }
    
    public void deleteUser(Long userId) {
        taskService.deleteAllTasksForUser(userId);
        repo.deleteById(userId);
    }
}
