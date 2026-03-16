package org.test.todolistapps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.test.todolistapps.dto.TaskDtoStatus;
import org.test.todolistapps.entities.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCreatedBy(Long userId);

    @Query("SELECT t FROM Task t WHERE t.status = 'completed'")
    List<Task> findCompletedTasks();

    @Query("SELECT t FROM Task t WHERE t.status = 'incomplete'")
    List<Task> findIncompleteTasks();

    @Query("SELECT t FROM Task t WHERE t.category.id = :categoryId")
    List<Task> findByCategoryId(@Param("categoryId") Long categoryId);
}
