package org.example.bcm.common.exception;

import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<StandardApiResponse<?>> handleServiceNotFoundException(ServiceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(StandardApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(StandardApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(TokenNotEnoughException.class)
    public ResponseEntity<StandardApiResponse<?>> handleTokenNotEnoughException(TokenNotEnoughException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(StandardApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(NotAllowedToJoinException.class)
    public ResponseEntity<StandardApiResponse<?>> handleNotAllowedToJoinException(NotAllowedToJoinException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(StandardApiResponse.error(ex.getMessage()));
    }
}
