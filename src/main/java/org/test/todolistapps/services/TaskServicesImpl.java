package org.test.todolistapps.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.todolistapps.dto.CreateTaskRequest;
import org.test.todolistapps.dto.TaskDtoStatus;
import org.test.todolistapps.dto.TaskResponse;
import org.test.todolistapps.dto.TaskResponseGetCategory;
import org.test.todolistapps.dto.UpdateTaskRequest;
import org.test.todolistapps.entities.Category;
import org.test.todolistapps.entities.Task;
import org.test.todolistapps.entities.User;
import org.test.todolistapps.repository.TaskRepository;
import org.test.todolistapps.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class TaskServicesImpl implements TaskServices {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;

    public TaskServicesImpl(TaskRepository taskRepository, UserRepository userRepository, CategoryService categoryService, ObjectMapper objectMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public TaskResponseGetCategory createTask(CreateTaskRequest request, String username) {
        log.info("Creating task for user: {}", username);
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryService.getCategoryEntityById(request.getCategoryId());
        if (category == null) {
            throw new RuntimeException("Category not found");
        }

        Task task = Task.builder()
                .taskName(request.getTaskName())
                .status("incomplete")
                .category(category)
                .createdBy(user.getId())
                .build();

        Task createdTask = taskRepository.save(task);
        return convertToTaskResponse(createdTask);
    }

    @Override
    public Task updateTask(Long id, UpdateTaskRequest request) {
        log.info("Updating task with id: {}", id);
        Task existingTask = taskRepository.findById(id).orElse(null);
        if (existingTask == null) {
            return null;
        }

        existingTask.setTaskName(request.getTaskName());
        existingTask.setStatus(request.getStatus());

        if (request.getCategoryId() != null) {
            Category category = categoryService.getCategoryEntityById(request.getCategoryId());
            if (category != null) {
                existingTask.setCategory(category);
            }
        }

        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getTasksByCreatedBy(Long userId) {
        return taskRepository.findByCreatedBy(userId);
    }

    @Override
    public List<Task> getCompletedTasks() {
        return taskRepository.findCompletedTasks();
    }

    @Override
    public List<Task> getIncompleteTasks() {
        return taskRepository.findIncompleteTasks();
    }

    @Override
    public List<TaskDtoStatus> getByStatus(String status) {
        List<Task> tasks = taskRepository.findByStatus(status);
        return tasks.stream()
                .map(task -> objectMapper.convertValue(task, TaskDtoStatus.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getMyTasks(String username) {
        log.info("Getting tasks for user: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return taskRepository.findByCreatedBy(user.getId());
    }

    private TaskResponseGetCategory convertToTaskResponse(Task task) {
        return TaskResponseGetCategory.builder()
                .taskName(task.getTaskName())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
