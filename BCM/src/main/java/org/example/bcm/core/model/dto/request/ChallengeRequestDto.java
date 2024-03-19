package org.example.bcm.core.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChallengeRequestDto {
    private Long challengerId;
    private Long tableId;
    private Integer numberOfParties;
}
