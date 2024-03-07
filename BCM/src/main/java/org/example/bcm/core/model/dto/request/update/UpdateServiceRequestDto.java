package org.example.bcm.core.model.dto.request.update;

import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

public class UpdateServiceRequestDto {
    @NotNull(message = "Id is required for update")
    @NotBlank(message = "Id is required for update")
    @Negative(message = "Id must be a positive number")
    @NotEmpty(message = "Id is required for update")
    private Long id;
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Insert image url")
    @NotBlank(message = "Insert image url")
    private String imageUrl;
}
