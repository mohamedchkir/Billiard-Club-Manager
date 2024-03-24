package org.example.bcm.core.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WaitingListResponseDto {
    private Long id;
    private String reservationDate;
    private String guestName;
    private UserSimpleResponseDto user;
    private TableResponseDto table;
}
