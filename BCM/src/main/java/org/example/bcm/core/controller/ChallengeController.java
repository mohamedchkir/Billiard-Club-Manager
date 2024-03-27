package org.example.bcm.core.controller;

import lombok.RequiredArgsConstructor;
import org.example.bcm.core.model.dto.request.ChallengeRequestDto;
import org.example.bcm.core.model.dto.response.ChallengeResponseDto;
import org.example.bcm.core.service.ChallengeService;
import org.example.bcm.shared.Const.AppEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasAuthority;

@RestController
@RequestMapping(AppEndpoint.CHALLENGE_ENDPOINT)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChallengeController {

    private final ChallengeService challengeService;

    @PreAuthorize("hasAuthority('CHALLENGE_WRITE')")
    @PostMapping("/create")
    public ResponseEntity<ChallengeResponseDto> createChallenge(@RequestBody ChallengeRequestDto challengeRequestDto) {
        ChallengeResponseDto createdChallenge = challengeService.createChallenge(challengeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChallenge);
    }

    @PreAuthorize("hasAuthority('CREATE_CHALLENGE')")
    @PostMapping("/{challengeId}/join")
    public ResponseEntity<ChallengeResponseDto> joinChallenge(@PathVariable Long challengeId, @RequestParam Long userId) {
        ChallengeResponseDto joinedChallenge = challengeService.joinChallenge(challengeId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(joinedChallenge);
    }
}
