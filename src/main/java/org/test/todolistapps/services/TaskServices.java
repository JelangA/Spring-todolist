package org.test.todolistapps.services;

import org.test.todolistapps.entities.Task;

import java.util.List;

public interface TaskServices {
    List<Task> getAllTasks();
    Task getTaskById(Long id);
    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
    List<Task> getTasksByCreatedBy(Long userId);
    List<Task> getCompletedTasks();
    List<Task> getIncompleteTasks();
}
