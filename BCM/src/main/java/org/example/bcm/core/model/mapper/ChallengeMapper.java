package org.example.bcm.core.model.mapper;

import org.example.bcm.core.model.dto.request.ChallengeRequestDto;
import org.example.bcm.core.model.dto.response.ChallengeResponseDto;
import org.example.bcm.core.model.entity.Challenge;
import org.example.bcm.core.model.entity.Table;
import org.example.bcm.core.model.entity.User;

public class ChallengeMapper {

    public static ChallengeResponseDto toDto(Challenge challenge) {
        return ChallengeResponseDto.builder()
                .id(challenge.getId())
                .numberOfParties(challenge.getNumberOfParties())
                .challengerId(challenge.getChallenger().getId())
                .adversaryId(challenge.getAdversary() != null ? challenge.getAdversary().getId() : null)
                .tableId(challenge.getTable().getId())
                .winnerId(challenge.getWinner() != null ? challenge.getWinner().getId() : null)
                .build();
    }

    public static Challenge toEntity(ChallengeRequestDto challengeRequestDto) {
        return Challenge.builder()
                .numberOfParties(challengeRequestDto.getNumberOfParties())
                .challenger(User.builder().id(challengeRequestDto.getChallengerId()).build())
                .table(Table.builder().id(challengeRequestDto.getTableId()).build())
                .build();
    }
}
