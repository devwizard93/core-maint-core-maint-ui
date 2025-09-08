package com.mycompany.coremaint.service;

import com.mycompany.coremaint.model.Category;

import java.util.List;

public interface CategoryService {


    Category createCategory(Category category);

    List<Category> getAllCategories();

    Category findCategoryById(Long id);

    void deleteById(Long id);

    Category updateCategoryById(Long id, Category category);


}
