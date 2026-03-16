package org.test.todolistapps.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.todolistapps.dto.CategoryRequest;
import org.test.todolistapps.dto.CategoryResponse;
import org.test.todolistapps.entities.Category;
import org.test.todolistapps.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        if (categoryResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        Category updatedCategory = categoryService.updateCategory(id, category);
        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        if (categoryResponse == null) {
            return ResponseEntity.notFound().build();
        }
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
