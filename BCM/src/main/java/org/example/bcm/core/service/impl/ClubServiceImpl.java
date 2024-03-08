package org.example.bcm.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.bcm.common.exception.ResourceNotFoundException;
import org.example.bcm.core.model.dto.request.ClubRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateClubRequestDto;
import org.example.bcm.core.model.dto.response.ClubResponseDto;
import org.example.bcm.core.model.entity.Club;
import org.example.bcm.core.repository.ClubRepository;
import org.example.bcm.core.service.ClubService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final ModelMapper modelMapper;

    @Override
    public ClubResponseDto createClub(ClubRequestDto clubRequestDto) {
        Club club = modelMapper.map(clubRequestDto, Club.class);
        Club savedClub = clubRepository.save(club);
        return modelMapper.map(savedClub, ClubResponseDto.class);
    }

    @Override
    public ClubResponseDto getClubById(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", clubId));
        return modelMapper.map(club, ClubResponseDto.class);
    }

    @Override
    public List<ClubResponseDto> getAllClubs() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream()
                .map(club -> modelMapper.map(club, ClubResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClubResponseDto updateClub(UpdateClubRequestDto updateClubRequestDto) {
        Club existingClub = clubRepository.findById(updateClubRequestDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", updateClubRequestDto.getId()));

        modelMapper.map(updateClubRequestDto, existingClub);
        Club updatedClub = clubRepository.save(existingClub);
        return modelMapper.map(updatedClub, ClubResponseDto.class);
    }

    @Override
    public void deleteClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", clubId));
        clubRepository.delete(club);
    }
}
