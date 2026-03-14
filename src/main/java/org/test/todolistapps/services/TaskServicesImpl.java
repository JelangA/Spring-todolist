package org.test.todolistapps.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.todolistapps.entities.Task;
import org.test.todolistapps.repository.TaskRepository;

import java.util.List;

@Service
@Transactional
public class TaskServicesImpl implements TaskServices {

    private final TaskRepository taskRepository;

    public TaskServicesImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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
}
