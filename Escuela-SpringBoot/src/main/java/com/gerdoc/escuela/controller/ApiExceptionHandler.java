package com.gerdoc.escuela.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.util.Map;
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,String>> integrity(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error","No se puede guardar o eliminar: hay una relación inexistente, duplicada o registros dependientes."));
    }
    @ExceptionHandler({MethodArgumentNotValidException.class,HttpMessageNotReadableException.class,jakarta.validation.ConstraintViolationException.class})
    public ResponseEntity<Map<String,String>> invalid(Exception ex) {
        return ResponseEntity.badRequest().body(Map.of("error","Revisa los campos obligatorios, longitudes y rangos numéricos."));
    }
}
