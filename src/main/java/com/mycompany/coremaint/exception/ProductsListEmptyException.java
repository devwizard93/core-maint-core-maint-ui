package com.mycompany.coremaint.exception;

public class ProductsListEmptyException extends RuntimeException{

   public ProductsListEmptyException(){
       super("Lista vacia");
   }

}
