package org.test.todolistapps.services;

import org.springframework.stereotype.Service;
import org.test.todolistapps.dto.TaskRequest;
import org.test.todolistapps.entities.Task;
import org.test.todolistapps.entities.User;
import org.test.todolistapps.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(TaskRequest request, User user) {
        Task task = new Task();
        task.setTaskName(request.getTaskName());
        task.setCategoryId(request.getCategoryId());
        task.setStatus("pending");
        task.setCreatedBy(user.getId());
        task.setCreatedAt(LocalDateTime.now().toString());
        return taskRepository.save(task);
    }

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByCreatedBy(user.getId());
    }
}
