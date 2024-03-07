package org.example.bcm.core.service.impl;

import org.example.bcm.core.model.dto.request.ClubRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateClubRequestDto;
import org.example.bcm.core.model.dto.response.ClubResponseDto;
import org.example.bcm.core.service.ClubService;

import java.util.List;

public class ClubServiceImpl implements ClubService {
    @Override
    public ClubResponseDto createClub(ClubRequestDto clubRequestDto) {
        return null;
    }

    @Override
    public ClubResponseDto getClubById(Long clubId) {
        return null;
    }

    @Override
    public List<ClubResponseDto> getAllClubs() {
        return null;
    }

    @Override
    public ClubResponseDto updateClub(UpdateClubRequestDto updateClubRequestDto) {
        return null;
    }

    @Override
    public void deleteClub(Long clubId) {

    }
}
