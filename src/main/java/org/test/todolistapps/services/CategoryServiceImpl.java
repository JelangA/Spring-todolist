package org.test.todolistapps.services;

import org.springframework.stereotype.Service;
import org.test.todolistapps.dto.CategoryResponse;
import org.test.todolistapps.dto.TaskResponse;
import org.test.todolistapps.dto.TaskResponseGetCategory;
import org.test.todolistapps.entities.Category;
import org.test.todolistapps.entities.Task;
import org.test.todolistapps.repository.CategoryRepository;
import org.test.todolistapps.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, TaskRepository taskRepository) {
        this.categoryRepository = categoryRepository;
        this.taskRepository = taskRepository;
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
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory != null) {
            existingCategory.setName(category.getName());
            existingCategory.setDescription(category.getDescription());
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
