package com.mycompany.coremaint.controller;

import com.mycompany.coremaint.model.Category;
import com.mycompany.coremaint.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoriaService;

    // Crear nueva categoría
    @PostMapping
    public ResponseEntity<Category> createCategoria(@RequestBody Category category) {
        Category nueva = categoriaService.createCategory(category);
        return ResponseEntity.ok(nueva);
    }

    // Listar todas las categorías
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoriaService.getAllCategories());
    }

    // Buscar una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable Long id) {
        Category categoria = categoriaService.findCategoryById(id);
        return (categoria != null)
                ? ResponseEntity.ok(categoria)
                : ResponseEntity.notFound().build();
    }

    // Eliminar una categoría por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar una categoría por ID
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategoryById(@PathVariable Long id, @RequestBody Category category) {
        Category updateCategory = categoriaService.findCategoryById(id);
        return (updateCategory != null)
                ? ResponseEntity.ok(updateCategory)
                : ResponseEntity.notFound().build();
    }
}
