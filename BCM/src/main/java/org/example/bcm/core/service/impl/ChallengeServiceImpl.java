package org.example.bcm.core.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bcm.common.exception.NotAllowedToJoinException;
import org.example.bcm.common.exception.ResourceNotFoundException;
import org.example.bcm.common.exception.TokenNotEnoughException;
import org.example.bcm.core.model.dto.request.ChallengeRequestDto;
import org.example.bcm.core.model.dto.response.ChallengeResponseDto;
import org.example.bcm.core.model.entity.Challenge;
import org.example.bcm.core.model.entity.Table;
import org.example.bcm.core.model.entity.User;
import org.example.bcm.core.model.mapper.ChallengeMapper;
import org.example.bcm.core.repository.ChallengeRepository;
import org.example.bcm.core.repository.TableRepository;
import org.example.bcm.core.repository.UserRepository;
import org.example.bcm.core.service.ChallengeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final TableRepository tableRepository;

    @Override
    @Transactional
    public ChallengeResponseDto createChallenge(ChallengeRequestDto challengeRequestDto) {
        Challenge challenge = ChallengeMapper.toEntity(challengeRequestDto);

        Table table = tableRepository.findById(challengeRequestDto.getTableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table", "id", challengeRequestDto.getTableId()));

        User creator = userRepository.findById(challengeRequestDto.getChallengerId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", challengeRequestDto.getChallengerId()));

        // Check if the table has already a challenge
        if (table.getChallenges().stream().anyMatch(c -> c.getAdversary() == null)) {
            throw new NotAllowedToJoinException("This table has already a challenge");
        }

        // Check if the creator has enough tokens
        if ((table.getTokensNeeded() * challenge.getNumberOfParties()) > creator.getNumberOfToken()){
            throw new TokenNotEnoughException("To create a challenge, you need " + table.getTokensNeeded() * challenge.getNumberOfParties() + " tokens");
        }

        // Decrease the number of tokens of the challenge creator
        creator.setNumberOfToken(creator.getNumberOfToken() - (table.getTokensNeeded() * challenge.getNumberOfParties()));
        userRepository.save(creator);

        // Set the table and the challenger of the challenge and save it
        challenge.setTable(table);
        challenge.setChallenger(creator);
        challenge.setNumberOfParties(challengeRequestDto.getNumberOfParties());
        Challenge createdChallenge = challengeRepository.save(challenge);

        return ChallengeMapper.toDto(createdChallenge);

    }

    @Override
    @Transactional
    public ChallengeResponseDto joinChallenge(Long challengeId, Long userId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge", "id", challengeId));

        if (challenge.getAdversary() != null) {
            throw new NotAllowedToJoinException("This challenge is already full");
        }

        challenge.setAdversary(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId)));

        Challenge joinedChallenge = challengeRepository.save(challenge);
        return ChallengeMapper.toDto(joinedChallenge);
    }
}
