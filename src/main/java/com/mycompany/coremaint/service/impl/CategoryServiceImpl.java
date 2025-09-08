package com.mycompany.coremaint.service.impl;

import com.mycompany.coremaint.exception.CategoriesListEmptyExcepcion;
import com.mycompany.coremaint.exception.CategoryNotFoundException;
import com.mycompany.coremaint.exception.CategoryInvalidException;
import com.mycompany.coremaint.model.Category;
import com.mycompany.coremaint.repository.CategoryRepository;
import com.mycompany.coremaint.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Agrega una nueva categoría al repositorio.
     * @param categoria objeto a guardar.
     * @return la categoría guardada.
     */
    @Override
    public Category createCategory(Category categoria) {
        if (categoria.getName() == null || categoria.getName().trim().isEmpty()) {
            throw new CategoryInvalidException();
        }
        return categoryRepository.save(categoria);
    }

    /**
     * Retorna todas las categorías. Lanza excepción si no hay ninguna.
     * @return lista de categorías.
     */
    @Override
    public List<Category> getAllCategories() {
        List<Category> list = categoryRepository.findAll();
        if (list.isEmpty()) {
            throw new CategoriesListEmptyExcepcion();
        }
        return list;
    }

    /**
     * Busca una categoría por su ID. Lanza excepción si no se encuentra.
     * @param id identificador.
     * @return la categoría encontrada.
     */
    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    /**
     * Elimina una categoría por su ID. Lanza excepción si no existe.
     * @param id identificador.
     */
    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
        categoryRepository.deleteById(id);
    }

    /**
     * Actualiza los datos de una categoría. Lanza excepción si no existe.
     * @param id identificador.
     * @param category datos nuevos.
     * @return categoría actualizada.
     */
    @Override
    public Category updateCategoryById(Long id, Category category) {
        var updateCat = findCategoryById(id);
        // Validaciones opcionales
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }

        // Actualizar campos necesarios
        updateCat.setName(category.getName());
        // Si tienes más atributos, actualízalos aquí.

        return categoryRepository.save(updateCat);
    }
}
