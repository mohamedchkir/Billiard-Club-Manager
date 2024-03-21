package org.example.bcm.core.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSimpleResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String telephone;
    private String email;
    private Integer numberOfToken;
    private CityResponseDto city;
    private String role;
}
