package org.example.bcm.core.service;

import org.example.bcm.core.model.dto.request.ClubRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateClubRequestDto;
import org.example.bcm.core.model.dto.response.ClubResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClubService {
    ClubResponseDto createClub(ClubRequestDto clubRequestDto);

    ClubResponseDto getClubById(Long clubId);

    Page<ClubResponseDto> getAllClubs(Pageable pageable);

    ClubResponseDto updateClub(UpdateClubRequestDto updateClubRequestDto);

    void deleteClub(Long clubId);

    Page<ClubResponseDto> filterClubs(Pageable pageable,String name, Long cityId);
}
