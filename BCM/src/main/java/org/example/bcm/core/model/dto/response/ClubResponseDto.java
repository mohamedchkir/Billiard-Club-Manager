package org.example.bcm.core.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class ClubResponseDto implements Serializable {
    private Long id;
    private String name;
    private String address;
    private String description;
    private LocalTime openingHour;
    private LocalTime closeHour;
    private Integer numberOfToken;

    private List<ServiceResponseDto> services;
}
