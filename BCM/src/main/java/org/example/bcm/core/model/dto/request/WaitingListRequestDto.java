package org.example.bcm.core.model.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WaitingListRequestDto {
    private Long id;
    private LocalDateTime reservationDate;
    private String guestName;
    private Long userId;
    private Long tableId;
}
