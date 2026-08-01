package com.mycompany.coremaint.exception;

public class ProductCreateException extends RuntimeException{

    public ProductCreateException(){
        super("el precio no puede ser negativo");
    }

}
