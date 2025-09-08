package com.mycompany.coremaint.service.impl;

import com.mycompany.coremaint.model.Sale;
import com.mycompany.coremaint.repository.SaleRepository;
import com.mycompany.coremaint.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {


    private final SaleRepository saleRepository;
    @Override
    public Sale createSale(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public List<Sale> getAllSale() {
        return saleRepository.findAll();
    }
}
