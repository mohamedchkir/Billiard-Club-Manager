package org.example.bcm.core.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CityResponseDto {
    private Long id;
    private String name;
}
