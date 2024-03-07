package org.example.bcm.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({DataBaseConstraintException.class, LogicValidationException.class})
    public ResponseEntity<StandardApiResponse<?>> handleCustomValidationException(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(StandardApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardApiResponse<?>> handleCustomValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .badRequest()
                .body(StandardApiResponse.validationErrors(ex.getBindingResult()));
    }
}
