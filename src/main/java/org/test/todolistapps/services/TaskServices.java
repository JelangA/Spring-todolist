package org.test.todolistapps.services;

import org.test.todolistapps.dto.CreateTaskRequest;
import org.test.todolistapps.dto.TaskDtoStatus;
import org.test.todolistapps.dto.TaskResponseGetCategory;
import org.test.todolistapps.dto.UpdateTaskRequest;
import org.test.todolistapps.entities.Task;

import java.util.List;

public interface TaskServices {
    List<Task> getAllTasks();
    Task getTaskById(Long id);
    TaskResponseGetCategory createTask(CreateTaskRequest request, String username);
    Task updateTask(Long id, UpdateTaskRequest request);
    void deleteTask(Long id);
    List<Task> getTasksByCreatedBy(Long userId);
    List<Task> getCompletedTasks();
    List<Task> getIncompleteTasks();
    List<TaskDtoStatus> getByStatus(String status);
    List<Task> getMyTasks(String username);
}
