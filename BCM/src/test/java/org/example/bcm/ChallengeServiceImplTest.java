package org.example.bcm;

import org.example.bcm.common.exception.NotAllowedToJoinException;
import org.example.bcm.common.exception.ResourceNotFoundException;
import org.example.bcm.common.exception.TokenNotEnoughException;
import org.example.bcm.core.model.dto.request.ChallengeRequestDto;
import org.example.bcm.core.model.dto.response.ChallengeResponseDto;
import org.example.bcm.core.model.entity.Challenge;
import org.example.bcm.core.model.entity.Table;
import org.example.bcm.core.model.entity.User;
import org.example.bcm.core.repository.ChallengeRepository;
import org.example.bcm.core.repository.TableRepository;
import org.example.bcm.core.repository.UserRepository;
import org.example.bcm.core.service.impl.ChallengeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ChallengeServiceImplTest {

    @Mock
    private ChallengeRepository challengeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private ChallengeServiceImpl challengeService;

    @Test
    void createChallenge_Success() {
        // Prepare test data
        ChallengeRequestDto requestDto = ChallengeRequestDto.builder()
                .challengerId(1L)
                .tableId(1L)
                .numberOfParties(2)
                .build();

        User challenger = new User();
        challenger.setId(1L);
        challenger.setNumberOfToken(10);

        Table table = new Table();
        table.setId(1L);
        table.setTokensNeeded(5);
        table.setChallenges(Set.of());

        Challenge challenge = new Challenge();
        challenge.setChallenger(challenger);
        challenge.setTable(table);
        challenge.setNumberOfParties(2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(challenger));
        when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
        when(challengeRepository.save(any(Challenge.class))).thenReturn(challenge);

        // Invoke the method
        ChallengeResponseDto responseDto = challengeService.createChallenge(requestDto);

        // Verify the result
        assertNotNull(responseDto);
    }

    @Test
    void createChallenge_UserNotFound_Failure() {
        // Prepare test data
        ChallengeRequestDto requestDto = ChallengeRequestDto.builder()
                .challengerId(1L)
                .tableId(1L)
                .numberOfParties(2)
                .build();

        // Invoke the method
        assertThrows(ResourceNotFoundException.class, () -> challengeService.createChallenge(requestDto));
    }

    @Test
    void createChallenge_TableNotFound_Failure() {
        // Prepare test data
        ChallengeRequestDto requestDto = ChallengeRequestDto.builder()
                .challengerId(1L)
                .tableId(1L)
                .numberOfParties(2)
                .build();

        User challenger = new User();
        challenger.setId(1L);

        // Invoke the method
        assertThrows(ResourceNotFoundException.class, () -> challengeService.createChallenge(requestDto));
    }
    @Test
    void createChallenge_ChallengerNotEnoughTokens_Failure() {
        // Prepare test data
        ChallengeRequestDto requestDto = ChallengeRequestDto.builder()
                .challengerId(1L)
                .tableId(1L)
                .numberOfParties(2)
                .build();

        User challenger = new User();
        challenger.setId(1L);
        challenger.setNumberOfToken(3); // Set fewer tokens than required

        Table table = new Table();
        table.setId(1L);
        table.setTokensNeeded(5);
        table.setChallenges(Set.of());

        when(userRepository.findById(1L)).thenReturn(Optional.of(challenger));
        when(tableRepository.findById(1L)).thenReturn(Optional.of(table));

        // Invoke the method
        assertThrows(TokenNotEnoughException.class, () -> challengeService.createChallenge(requestDto));
    }

    @Test
    void joinChallenge_Success() {
        // Prepare test data
        long challengeId = 1L;
        long userId = 1L;

        // Create adversary user
        User adversary = new User();
        adversary.setId(userId);
        adversary.setNumberOfToken(20); // Sufficient tokens

        // Create challenger user
        User challenger = new User();
        challenger.setId(2L);
        challenger.setNumberOfToken(10);

        // Create table
        Table table = new Table();
        table.setTokensNeeded(5); // Tokens needed for the challenge

        // Create challenge
        Challenge challenge = new Challenge();
        challenge.setId(challengeId);
        challenge.setChallenger(challenger);
        challenge.setTable(table);
        challenge.setNumberOfParties(2);

        table.setChallenges(Set.of(challenge));

        // Stub repository method calls
        when(challengeRepository.findById(challengeId)).thenReturn(Optional.of(challenge));
        when(userRepository.findById(userId)).thenReturn(Optional.of(adversary));
        when(challengeRepository.save(any(Challenge.class))).thenReturn(challenge);

        // Invoke the method
        ChallengeResponseDto responseDto = challengeService.joinChallenge(challengeId, userId);

        // Verify the result
        assertNotNull(responseDto);
    }

    @Test
    void joinChallenge_ChallengeNotFound_Failure() {
        // Prepare test data
        long challengeId = 1L;
        long userId = 2L;

        // Stub repository method calls
        when(challengeRepository.findById(challengeId)).thenReturn(Optional.empty());

        // Verify that the method throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class,
                () -> challengeService.joinChallenge(challengeId, userId));
    }

    @Test
    void joinChallenge_UserNotFound_Failure() {
        // Prepare test data
        long challengeId = 1L;
        long userId = 2L;

        Challenge challenge = new Challenge();
        challenge.setId(challengeId);

        // Stub repository method calls
        when(challengeRepository.findById(challengeId)).thenReturn(Optional.of(challenge));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Verify that the method throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class,
                () -> challengeService.joinChallenge(challengeId, userId));
    }

    @Test
    void joinChallenge_ChallengeAlreadyFull_Failure() {
        // Prepare test data
        long challengeId = 1L;
        long userId = 2L;

        Challenge challenge = new Challenge();
        challenge.setId(challengeId);
        challenge.setAdversary(new User()); // Challenge already has an adversary

        // Stub repository method calls
        when(challengeRepository.findById(challengeId)).thenReturn(Optional.of(challenge));

        // Verify that the method throws NotAllowedToJoinException
        assertThrows(NotAllowedToJoinException.class,
                () -> challengeService.joinChallenge(challengeId, userId));
    }

    @Test
    void joinChallenge_NotAllowedToJoinOwnChallenge_Failure() {
        // Prepare test data
        long challengeId = 1L;
        long userId = 2L;

        Challenge challenge = new Challenge();
        challenge.setId(challengeId);
        User challenger = new User();
        challenger.setId(userId); // Set the challenger same as the joining user
        challenge.setChallenger(challenger);

        // Stub repository method calls
        when(challengeRepository.findById(challengeId)).thenReturn(Optional.of(challenge));
        when(userRepository.findById(userId)).thenReturn(Optional.of(challenger));

        // Verify that the method throws NotAllowedToJoinException
        assertThrows(NotAllowedToJoinException.class,
                () -> challengeService.joinChallenge(challengeId, userId));
    }

    @Test
    void joinChallenge_InsufficientTokensToJoin_Failure() {
        // Prepare test data
        long challengeId = 1L;
        long userId = 2L;

        User challenger = new User();
        challenger.setId(1L);
        challenger.setNumberOfToken(30);

        Table table = new Table();
        table.setTokensNeeded(5);

        User adversary = new User();
        adversary.setId(userId);
        adversary.setNumberOfToken(2);

        Challenge challenge = new Challenge();
        challenge.setId(challengeId);
        challenge.setChallenger(challenger);
        challenge.setTable(table);
        challenge.setNumberOfParties(3);

        when(challengeRepository.findById(challengeId)).thenReturn(Optional.of(challenge));
        when(userRepository.findById(userId)).thenReturn(Optional.of(adversary));

        assertThrows(TokenNotEnoughException.class,
                () -> challengeService.joinChallenge(challengeId, userId));
    }



}
