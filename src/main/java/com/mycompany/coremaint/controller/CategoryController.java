package com.mycompany.coremaint.controller;

import com.mycompany.coremaint.dto.ApiResponse;
import com.mycompany.coremaint.model.Category;
import com.mycompany.coremaint.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
//comentario actualizar
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Crear nueva categoría
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        var savedCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }


    // Listar todas las categorías
    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        var categories = categoryService.getAllCategories();
        ApiResponse<List<Category>> response = new ApiResponse<>();

        if (categories.isEmpty()) {
            response.setData(Collections.emptyList());
            response.setMessage("No hay categorías registradas");
        } else {
            response.setData(categories);
            response.setMessage("Categorías encontradas");
        }

        return ResponseEntity.ok(response);
    }

    // Buscar una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable Long id) {
        //Logica de presentacion
        Category categoria = categoryService.findCategoryById(id);
        return ResponseEntity.ok(categoria);
    }

    // Eliminar una categoría por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar una categoría por ID
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategoryById(@PathVariable Long id, @RequestBody Category category) {
        Category updateCategory = categoryService.findCategoryById(id);
        return ResponseEntity.ok(updateCategory);
    }
}
