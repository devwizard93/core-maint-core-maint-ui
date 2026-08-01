package com.mycompany.coremaint.service.impl;

import com.mycompany.coremaint.model.Product;
import com.mycompany.coremaint.model.Sale;
import com.mycompany.coremaint.repository.ProductRepository;
import com.mycompany.coremaint.repository.SaleRepository;
import com.mycompany.coremaint.service.SaleService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {


    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    @Override
    public Sale createSale(Sale sale) {

        double total = 0.0;
        for (String productName : sale.getProductList()) {
            Product product = productRepository.findByName(productName)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productName));


            total += product.getPrice();
        }
        Sale saleDTO = new Sale();
        LocalDateTime localDateTime = LocalDateTime.now();
        saleDTO.setId(sale.getId());
        saleDTO.setStartDate(localDateTime);
        saleDTO.setProductList(sale.getProductList());
        saleDTO.setPrice(total);
        return saleRepository.save(saleDTO);
    }

    @Override
    public List<Sale> getAllSale() {
        return saleRepository.findAll();
    }
}
