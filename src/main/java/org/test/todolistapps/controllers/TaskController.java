package org.test.todolistapps.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.test.todolistapps.dto.CreateTaskRequest;
import org.test.todolistapps.dto.TaskDtoStatus;
import org.test.todolistapps.dto.TaskResponseGetCategory;
import org.test.todolistapps.dto.UpdateTaskRequest;
import org.test.todolistapps.entities.Category;
import org.test.todolistapps.entities.Task;
import org.test.todolistapps.entities.User;
import org.test.todolistapps.repository.UserRepository;
import org.test.todolistapps.services.CategoryService;
import org.test.todolistapps.services.TaskServices;

import java.util.List;

@RequestMapping("/api/tasks")
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskServices taskServices;
    private final CategoryService categoryService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TaskResponseGetCategory> createTask(@RequestBody CreateTaskRequest request, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        Category category = categoryService.getCategoryEntityById(request.getCategoryId());
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }

        Task task = Task.builder()
                .taskName(request.getTaskName())
                .status("incomplete")
                .category(category)
                .createdBy(user.getId())
                .build();

        Task createdTask = taskServices.createTask(task);
        TaskResponseGetCategory response = convertToTaskResponse(createdTask);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskServices.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskServices.getTaskById(id);
        if (task != null) {
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/my-tasks")
    public ResponseEntity<List<Task>> getMyTasks(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Task> tasks = taskServices.getTasksByCreatedBy(user.getId());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Task>> getCompletedTasks() {
        List<Task> tasks = taskServices.getCompletedTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/incomplete")
    public ResponseEntity<List<Task>> getIncompleteTasks() {
        List<Task> tasks = taskServices.getIncompleteTasks();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest request) {
        Task existingTask = taskServices.getTaskById(id);
        if (existingTask == null) {
            return ResponseEntity.notFound().build();
        }

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryService.getCategoryEntityById(request.getCategoryId());
        }

        Task updatedTask = Task.builder()
                .id(id)
                .taskName(request.getTaskName())
                .status(request.getStatus())
                .category(category != null ? category : existingTask.getCategory())
                .createdBy(existingTask.getCreatedBy())
                .createdAt(existingTask.getCreatedAt())
                .build();

        Task result = taskServices.updateTask(id, updatedTask);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Task task = taskServices.getTaskById(id);
        if (task != null) {
            taskServices.deleteTask(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDtoStatus>> getTasksByStatus(@PathVariable String status) {
        List<TaskDtoStatus> tasksResponse = taskServices.getByStatus(status);
        return ResponseEntity.ok(tasksResponse);
    }

    private TaskResponseGetCategory convertToTaskResponse(Task task) {
        return TaskResponseGetCategory.builder()
                .taskName(task.getTaskName())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

}
