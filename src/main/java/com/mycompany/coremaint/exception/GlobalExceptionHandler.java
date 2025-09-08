package com.mycompany.coremaint.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductoNotFound(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> CategoriaNotFound(CategoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CategoriesListEmptyExcepcion.class)
    public ResponseEntity<String> CategoriaListIsEmptyExcepcion(CategoriesListEmptyExcepcion ex) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ex.getMessage());
    }

    @ExceptionHandler(ProductsListEmptyException.class)
    public ResponseEntity<String> ProductosListIsEmptyExcepcion(ProductsListEmptyException ex) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ex.getMessage());
    }

    @ExceptionHandler(CategoryInvalidException.class)
    public ResponseEntity<String> CategoryInvalidException(CategoryInvalidException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ex.getMessage());
    }

    @ExceptionHandler(ProductCreateException.class)
    public ResponseEntity<String> ProductCreateException(ProductCreateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }




}
