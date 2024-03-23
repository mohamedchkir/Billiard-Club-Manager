package org.example.bcm.core.model.dto.request.update;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateUserRequestDto {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "First name is required")
    @NotEmpty(message = "First name is required")
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    @NotEmpty(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Telephone is required")
    @Pattern(regexp = "^(07|06)\\d{8}$", message = "Invalid telephone number format")
    private String telephone;

    @NotNull(message = "City ID is required")
    private Long cityId;

    @NotNull(message = "Number of tokens is required")
    @Positive(message = "Number of tokens must be a positive value")
    private Integer numberOfToken;

    @NotNull(message = "Image is required")
    @NotEmpty(message = "Image is required")
    @NotBlank(message = "Image is required")
    private String imageUrl;
}
