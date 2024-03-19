package org.example.bcm;

import org.example.bcm.common.exception.ResourceNotFoundException;
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
}
