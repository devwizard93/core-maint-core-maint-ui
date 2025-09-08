// --- Implementación del Servicio ---
package com.mycompany.coremaint.service.impl;

import com.mycompany.coremaint.exception.ProductCreateException;
import com.mycompany.coremaint.exception.ProductNotFoundException;
import com.mycompany.coremaint.exception.ProductsListEmptyException;
import com.mycompany.coremaint.model.Product;
import com.mycompany.coremaint.repository.ProductRepository;
import com.mycompany.coremaint.service.CategoryService;
import com.mycompany.coremaint.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    @Override
    public List<Product> getAllProducts() {
        final var products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductsListEmptyException();
        }
        return products;
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product createProduct(Product product) {
        categoryService.findCategoryById(product.getIdCategory());
        if (product.getPrice() < 0) {
            throw new ProductCreateException();
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProductById(Long id, Product afterProduct) {
        var productoOptional = productRepository.findById(id);
        if (productoOptional.isEmpty()) {
            throw new ProductNotFoundException(id);
        }
        Product postProducto = productoOptional.get();
        postProducto.setName(afterProduct.getName());
        postProducto.setPrice(afterProduct.getPrice());
        return productRepository.save(postProducto);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id);
        productRepository.deleteById(id);
    }





}
