package org.example.bcm.core.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
public class CityRequestDto {
    @NotBlank(message = "Name is required")
    private String name;

}
