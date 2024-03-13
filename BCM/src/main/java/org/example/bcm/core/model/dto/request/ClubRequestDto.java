package org.example.bcm.core.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
public class ClubRequestDto {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Opening hour is required")
    private LocalTime openingHour;

    @NotNull(message = "Close hour is required")
    private LocalTime closeHour;

    private Integer numberOfToken;

    @NotNull(message = "City ID is required")
    private Long cityId;

    @NotNull(message = "Services are required")
    @Size(min = 1, message = "At least one service is required")
    private Set<Long> serviceIds;

}
