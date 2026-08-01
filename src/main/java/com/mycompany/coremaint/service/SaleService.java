package com.mycompany.coremaint.service;

import com.mycompany.coremaint.model.Sale;

import java.util.List;

public interface SaleService {

    Sale createSale(Sale sale);
    List<Sale> getAllSale();

}
