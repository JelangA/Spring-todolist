package org.test.todolistapps.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.todolistapps.dto.CategoryRequest;
import org.test.todolistapps.dto.CategoryResponse;
import org.test.todolistapps.entities.Category;
import org.test.todolistapps.services.CategoryService;
import org.test.todolistapps.utils.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody CategoryRequest request) {
        Category createdCategory = categoryService.createCategory(request);
        return ResponseEntity.ok(ApiResponse.success("Category created successfully", createdCategory, 201));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.success("All categories retrieved", categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        if (categoryResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success("Category retrieved", categoryResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request) {
        Category updatedCategory = categoryService.updateCategory(id, request);
        if (updatedCategory != null) {
            return ResponseEntity.ok(ApiResponse.success("Category updated successfully", updatedCategory));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        if (categoryResponse == null) {
            return ResponseEntity.notFound().build();
        }
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully", null));
    }
}
