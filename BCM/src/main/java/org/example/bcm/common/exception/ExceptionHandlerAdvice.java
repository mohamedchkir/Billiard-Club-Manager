package org.example.bcm.common.exception;

import org.example.bcm.core.model.dto.response.StandardApiResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

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
