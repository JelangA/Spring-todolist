package org.test.todolistapps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.todolistapps.entities.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByCreatedBy(int createdBy);
}
