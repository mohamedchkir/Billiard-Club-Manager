package org.example.bcm.core.model.dto.response;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

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
