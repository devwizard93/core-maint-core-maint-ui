package com.mycompany.coremaint.service;

import com.mycompany.coremaint.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product findProductById(Long id);

    Product createProduct(Product product);

    Product updateProductById(Long id, Product product);

    void deleteProductById(Long id);
}