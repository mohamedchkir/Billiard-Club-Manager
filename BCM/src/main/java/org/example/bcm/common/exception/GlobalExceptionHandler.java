package org.example.bcm.common.exception;

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
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponses> handleResponseStatusException(ResponseStatusException ex) {
        HttpStatusCode statusCode = ex.getStatusCode();
        String message = ex.getReason();
        ErrorResponses errorResponse = new ErrorResponses(statusCode.value(), message);
        return ResponseEntity.status(statusCode).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponses> handleIllegalArgumentException(IllegalArgumentException ex) {
        int statusCode = HttpStatus.BAD_REQUEST.value();
        String message = ex.getMessage();
        ErrorResponses errorResponse = new ErrorResponses(statusCode, message);
        return ResponseEntity.status(statusCode).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleCustomValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Validation Failed");
        response.put("errors", new HashMap<>());

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            ((Map<String, Object>) response.get("errors")).put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(response);
    }

    private record ErrorResponses(int statusCode, String message) {
    }
}
