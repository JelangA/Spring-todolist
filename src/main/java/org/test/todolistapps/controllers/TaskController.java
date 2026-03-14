package org.test.todolistapps.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.test.todolistapps.dto.TaskRequest;
import org.test.todolistapps.entities.Task;
import org.test.todolistapps.entities.User;
import org.test.todolistapps.services.TaskService;

import java.util.List;

@RequestMapping("/tasks")
@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Task task = taskService.createTask(request, currentUser);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        List<Task> tasks = taskService.getTasksByUser(currentUser);
        return ResponseEntity.ok(tasks);
    }
}
