package org.test.todolistapps.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.test.todolistapps.dto.CreateTaskRequest;
import org.test.todolistapps.dto.TaskDtoStatus;
import org.test.todolistapps.dto.TaskResponseGetCategory;
import org.test.todolistapps.dto.UpdateTaskRequest;
import org.test.todolistapps.entities.Task;
import org.test.todolistapps.services.TaskServices;
import org.test.todolistapps.utils.ApiResponse;

import java.util.List;

@RequestMapping("/api/tasks")
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskServices taskServices;

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponseGetCategory>> createTask(@RequestBody CreateTaskRequest request, Authentication authentication) {
        String username = authentication.getName();
        TaskResponseGetCategory response = taskServices.createTask(request, username);
        return ResponseEntity.ok(ApiResponse.success("Task created successfully", response, 201));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Task>>> getAllTasks() {
        List<Task> tasks = taskServices.getAllTasks();
        return ResponseEntity.ok(ApiResponse.success("All tasks retrieved", tasks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> getTaskById(@PathVariable Long id) {
        Task task = taskServices.getTaskById(id);
        if (task != null) {
            return ResponseEntity.ok(ApiResponse.success("Task retrieved", task));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/my-tasks")
    public ResponseEntity<ApiResponse<List<Task>>> getMyTasks(Authentication authentication) {
        String username = authentication.getName();
        List<Task> tasks = taskServices.getMyTasks(username);
        return ResponseEntity.ok(ApiResponse.success("Your tasks retrieved", tasks));
    }

    @GetMapping("/completed")
    public ResponseEntity<ApiResponse<List<Task>>> getCompletedTasks() {
        List<Task> tasks = taskServices.getCompletedTasks();
        return ResponseEntity.ok(ApiResponse.success("Completed tasks retrieved", tasks));
    }

    @GetMapping("/incomplete")
    public ResponseEntity<ApiResponse<List<Task>>> getIncompleteTasks() {
        List<Task> tasks = taskServices.getIncompleteTasks();
        return ResponseEntity.ok(ApiResponse.success("Incomplete tasks retrieved", tasks));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest request) {
        Task updatedTask = taskServices.updateTask(id, request);
        if (updatedTask != null) {
            return ResponseEntity.ok(ApiResponse.success("Task updated successfully", updatedTask));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id) {
        Task task = taskServices.getTaskById(id);
        if (task != null) {
            taskServices.deleteTask(id);
            return ResponseEntity.ok(ApiResponse.success("Task deleted successfully", null));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<TaskDtoStatus>>> getTasksByStatus(@PathVariable String status) {
        List<TaskDtoStatus> tasksResponse = taskServices.getByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Tasks by status retrieved", tasksResponse));
    }

}
