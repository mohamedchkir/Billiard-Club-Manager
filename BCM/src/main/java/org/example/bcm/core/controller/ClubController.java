package org.example.bcm.core.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bcm.core.model.dto.request.ClubRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateClubRequestDto;
import org.example.bcm.core.model.dto.response.ClubResponseDto;
import org.example.bcm.core.service.ClubService;
import org.example.bcm.core.service.S3Service;
import org.example.bcm.shared.Const.AppEndpoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(AppEndpoint.CLUB_ENDPOINT)
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;
    private final S3Service s3Service;

    @PreAuthorize("hasAuthority('WRITE_CLUB')")
    @PostMapping
    public ResponseEntity<ClubResponseDto> createClub(@Valid @ModelAttribute ClubRequestDto clubRequestDto) {
        String imageUrl = s3Service.uploadFile(clubRequestDto.getFile());
        clubRequestDto.setImageUrl(imageUrl);
        ClubResponseDto createdClub = clubService.createClub(clubRequestDto);
        return new ResponseEntity<>(createdClub, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('READ_CLUB')")
    @GetMapping("/{clubId}")
    public ResponseEntity<ClubResponseDto> getClubById(@PathVariable Long clubId) {
        ClubResponseDto club = clubService.getClubById(clubId);
        return ResponseEntity.ok(club);
    }


    @PreAuthorize("hasAuthority('UPDATE_CLUB')")
    @PutMapping()
    public ResponseEntity<ClubResponseDto> updateClub(@Valid @ModelAttribute UpdateClubRequestDto updateClubRequestDto) {
        MultipartFile file = updateClubRequestDto.getFile();
        if (file != null && !file.isEmpty()) {
            String imageUrl = s3Service.uploadFile(file);
            updateClubRequestDto.setImageUrl(imageUrl);
        }
        ClubResponseDto updatedClub = clubService.updateClub(updateClubRequestDto);
        return ResponseEntity.ok(updatedClub);
    }

    @PreAuthorize("hasAuthority('DELETE_CLUB')")
    @DeleteMapping("/{clubId}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long clubId) {
        clubService.deleteClub(clubId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('READ_CLUB')")
    @GetMapping("/search")
    public ResponseEntity<Page<ClubResponseDto>> searchClubs(@RequestParam(required = false) String name, @RequestParam(required = false) Long cityId, @RequestParam Integer page, @RequestParam Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<ClubResponseDto> clubs = clubService.filterClubs(pageable, name, cityId);
        return ResponseEntity.ok(clubs);
    }

    @PreAuthorize("hasAuthority('READ_CLUB')")
    @GetMapping
    public ResponseEntity<Page<ClubResponseDto>> getAllClubs(@RequestParam Integer page, @RequestParam Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<ClubResponseDto> clubs = clubService.getAllClubs(pageable);
        return ResponseEntity.ok(clubs);
    }
}
