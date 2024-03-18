package org.example.bcm.core.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bcm.core.model.dto.request.ClubRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateClubRequestDto;
import org.example.bcm.core.model.dto.response.ClubResponseDto;
import org.example.bcm.core.service.ClubService;
import org.example.bcm.shared.Const.AppEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppEndpoint.CLUB_ENDPOINT)
@RequiredArgsConstructor
public class ClubController {


    private final ClubService clubService;

    @PostMapping
    public ResponseEntity<ClubResponseDto> createClub(@Valid @RequestBody ClubRequestDto clubRequestDto) {
        ClubResponseDto createdClub = clubService.createClub(clubRequestDto);
        return new ResponseEntity<>(createdClub, HttpStatus.CREATED);
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<ClubResponseDto> getClubById(@PathVariable Long clubId) {
        ClubResponseDto club = clubService.getClubById(clubId);
        return ResponseEntity.ok(club);
    }

    @GetMapping
    public ResponseEntity<List<ClubResponseDto>> getAllClubs() {
        List<ClubResponseDto> clubs = clubService.getAllClubs();
        return ResponseEntity.ok(clubs);
    }

    @PutMapping()
    public ResponseEntity<ClubResponseDto> updateClub(@Valid @RequestBody UpdateClubRequestDto updateClubRequestDto) {
        ClubResponseDto updatedClub = clubService.updateClub(updateClubRequestDto);
        return ResponseEntity.ok(updatedClub);
    }

    @DeleteMapping("/{clubId}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long clubId) {
        clubService.deleteClub(clubId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClubResponseDto>> searchClubs(@RequestParam(required = false) String firstName ,@RequestParam(required = false) Long cityId) {
        List<ClubResponseDto> clubs = clubService.filterClubs(firstName, cityId);
        return ResponseEntity.ok(clubs);
    }
}
