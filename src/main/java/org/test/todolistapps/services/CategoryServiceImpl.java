package org.test.todolistapps.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.test.todolistapps.dto.CategoryRequest;
import org.test.todolistapps.dto.CategoryResponse;
import org.test.todolistapps.dto.TaskResponse;
import org.test.todolistapps.entities.Category;
import org.test.todolistapps.entities.Task;
import org.test.todolistapps.repository.CategoryRepository;
import org.test.todolistapps.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;
    private final TaskRepository taskRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, TaskRepository taskRepository, ObjectMapper objectMapper) {
        this.categoryRepository = categoryRepository;
        this.taskRepository = taskRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {

        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return null;
        }

        List<Task> tasks = taskRepository.findByCategoryId(id);

        List<TaskResponse> taskResponses = tasks.stream()
                .map(this::mapTaskToTaskResponse)
                .collect(Collectors.toList());

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .tasks(taskResponses)
                .build();
    }

    @Override
    public Category createCategory(CategoryRequest request) {
        log.info("Creating category: {}", request.getName());
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, CategoryRequest request) {
        log.info("Updating category with id: {}", id);
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory != null) {
            existingCategory.setName(request.getName());
            existingCategory.setDescription(request.getDescription());
            return categoryRepository.save(existingCategory);
        }
        return null;
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryEntityById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    private TaskResponse mapTaskToTaskResponse(Task task) {
        return TaskResponse.builder()
                .taskName(task.getTaskName())
                .createdAt(task.getCreatedAt())
                .build();
    }
}
