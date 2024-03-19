package org.example.bcm.core.service;

import org.example.bcm.core.model.dto.request.ChallengeRequestDto;
import org.example.bcm.core.model.dto.response.ChallengeResponseDto;

public interface ChallengeService {
    ChallengeResponseDto createChallenge(ChallengeRequestDto challengeRequestDto);
    ChallengeResponseDto joinChallenge(Long challengeId, Long userId);
}
