    package com.mycompany.coremaint.exception;

    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.time.LocalDateTime;
    import java.util.Map;

    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(DuplicateNameException.class)
        public ResponseEntity<Map<String, Object>> handleDuplicateName(DuplicateNameException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "timestamp", LocalDateTime.now(),
                    "status", HttpStatus.CONFLICT.value(),
                    "error", "Nombre duplicado",
                    "message", ex.getMessage()
            ));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "timestamp", LocalDateTime.now(),
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error", "Error interno del servidor",
                    "message", ex.getMessage()
            ));
        }

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        @ExceptionHandler(ProductCreateException.class)
        public ResponseEntity<String> ProductCreateException(ProductCreateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }




    }
