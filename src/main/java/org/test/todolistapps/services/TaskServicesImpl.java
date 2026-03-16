package org.test.todolistapps.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.todolistapps.dto.TaskDtoStatus;
import org.test.todolistapps.dto.TaskResponse;
import org.test.todolistapps.entities.Task;
import org.test.todolistapps.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Locale.filter;

@Service
@Transactional
public class TaskServicesImpl implements TaskServices {

    private final TaskRepository taskRepository;
    private final ObjectMapper objectMapper;

    public TaskServicesImpl(TaskRepository taskRepository, ObjectMapper objectMapper) {
        this.taskRepository = taskRepository;
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
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        Task existingTask = taskRepository.findById(id).orElse(null);
        if (existingTask != null) {
            existingTask.setTaskName(task.getTaskName());
            existingTask.setStatus(task.getStatus());
            existingTask.setCategory(task.getCategory());
            return taskRepository.save(existingTask);
        }
        return null;
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

        List<Task> tasks = taskRepository.findAll();

        return tasks.stream()
                .map(task -> objectMapper.convertValue(task, TaskDtoStatus.class))
                .toList();
    }

}
