package org.example.bcm.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
@Setter
public class StandardApiResponse<T> {
    private String status;
    private String message;
    private T data;
    private Map<String, String> errors;

    public static <T> StandardApiResponse<T> success(String message, T data) {
        return StandardApiResponse.<T>builder().status("success").message(message).data(data).build();
    }

    public static StandardApiResponse<?> error(String message) {
        return StandardApiResponse.builder().status("failed").message(message).build();
    }

    public static StandardApiResponse<?> validationErrors(BindingResult bindingResult) {
        StandardApiResponse<?> response = StandardApiResponse.error("validation failed");
        response.errors = new HashMap<>();

        for (FieldError error : bindingResult.getFieldErrors()) {
            response.errors.put(error.getField(), error.getDefaultMessage());
        }

        return response;
    }
}