package org.test.todolistapps.services;

import org.test.todolistapps.dto.CategoryRequest;
import org.test.todolistapps.dto.CategoryResponse;
import org.test.todolistapps.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    Category createCategory(CategoryRequest request);

    Category updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);

    Category getCategoryEntityById(Long id);
}
