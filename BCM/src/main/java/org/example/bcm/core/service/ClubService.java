package org.example.bcm.core.service;

import org.example.bcm.core.model.dto.request.ClubRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateClubRequestDto;
import org.example.bcm.core.model.dto.response.ClubResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClubService {
    ClubResponseDto createClub(ClubRequestDto clubRequestDto);

    ClubResponseDto getClubById(Long clubId);

    List<ClubResponseDto> getAllClubs();

    ClubResponseDto updateClub(UpdateClubRequestDto updateClubRequestDto);

    void deleteClub(Long clubId);
}
