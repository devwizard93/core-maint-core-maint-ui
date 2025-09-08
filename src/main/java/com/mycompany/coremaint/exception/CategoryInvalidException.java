package com.mycompany.coremaint.exception;

public class CategoryInvalidException extends RuntimeException{

    public CategoryInvalidException(){
        super("El nombre no puede estar vacio");
    }

}
