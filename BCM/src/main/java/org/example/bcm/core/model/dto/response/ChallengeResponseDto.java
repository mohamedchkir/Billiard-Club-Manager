package org.example.bcm.core.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChallengeResponseDto {
    private Long id;
    private Long challengerId;
    private Long adversaryId;
    private Long winnerId;
    private Long tableId;
    private Integer numberOfParties;
}
