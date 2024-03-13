package org.example.bcm.core.model.dto.request.update;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserCityRequestDto {
    @NotNull(message = "City ID is required")
    private Long cityId;
    @NotNull(message = "Id is required for update")
    private Long userId;
}
