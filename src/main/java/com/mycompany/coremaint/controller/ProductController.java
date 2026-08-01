
// --- Controlador ---
package com.mycompany.coremaint.controller;

import com.mycompany.coremaint.model.Product;
import com.mycompany.coremaint.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Tag(name = "Producto", description = "API para gestión de productos")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Listar todos los productos")
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por ID")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        final var product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }


    @PostMapping
    @Operation(summary = "Crear un nuevo producto")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        final var newProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        final var updateProduct = productService.updateProductById(id, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(updateProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

}