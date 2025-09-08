package com.mycompany.coremaint.repository;

import com.mycompany.coremaint.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}