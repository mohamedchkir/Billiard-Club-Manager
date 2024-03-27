package org.example.bcm.core.model.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class ApiResponse<T> implements Serializable {
    private String status;
    private String message;
    private T data;
    private Map<String, String> errors;

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder().status("success").message(message).data(data).build();
    }

    public static ApiResponse<?> error(String message) {
        return ApiResponse.builder().status("failed").message(message).build();
    }

    public static ApiResponse<?> validationErrors(BindingResult bindingResult) {
        ApiResponse<?> response = ApiResponse.error("validation failed");
        response.errors = new HashMap<>();

        for (FieldError error : bindingResult.getFieldErrors()) {
            response.errors.put(error.getField(), error.getDefaultMessage());
        }

        return response;
    }
}
