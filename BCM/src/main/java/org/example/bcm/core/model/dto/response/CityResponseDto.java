package org.example.bcm.core.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityResponseDto {
    private Long id;
    private String name;
}
