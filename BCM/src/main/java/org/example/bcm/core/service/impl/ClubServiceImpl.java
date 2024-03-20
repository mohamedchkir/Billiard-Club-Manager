package org.example.bcm.core.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.bcm.common.exception.ResourceNotFoundException;
import org.example.bcm.common.exception.ServiceNotFoundException;
import org.example.bcm.core.model.entity.Table;
import org.example.bcm.core.model.mapper.ClubMapper;
import org.example.bcm.core.model.dto.request.ClubRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateClubRequestDto;
import org.example.bcm.core.model.dto.response.ClubResponseDto;
import org.example.bcm.core.model.entity.City;
import org.example.bcm.core.model.entity.Club;
import org.example.bcm.core.model.entity.Service;
import org.example.bcm.core.repository.CityRepository;
import org.example.bcm.core.repository.ClubRepository;
import org.example.bcm.core.repository.ServiceRepository;
import org.example.bcm.core.repository.TableRepository;
import org.example.bcm.core.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final CityRepository cityRepository;
    private final ServiceRepository serviceRepository;
    private final TableRepository tableRepository;

    @Override
    public ClubResponseDto createClub(ClubRequestDto clubRequestDto) {
        // Check if the City exists
        City city = cityRepository.findById(clubRequestDto.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City", "id", clubRequestDto.getCityId()));

        // Check if the Services exist
        List<Service> services = serviceRepository.findAllById(clubRequestDto.getServiceIds());
        if (services.size() != clubRequestDto.getServiceIds().size()) {
            throw new ResourceNotFoundException("Service", "ids", clubRequestDto.getServiceIds());
        }

        Club club = ClubMapper.toEntity(clubRequestDto, city, new HashSet<>(services));

        Club savedClub = clubRepository.save(club);
        return ClubMapper.toDto(savedClub);
    }

    @Override
    public ClubResponseDto getClubById(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", clubId));
        return ClubMapper.toDto(club);
    }

    @Override
    public List<ClubResponseDto> getAllClubs() {
        List<Club> clubs = clubRepository.findAll();
        if (clubs.isEmpty()) {
            throw new ServiceNotFoundException("No clubs found");
        }
        return clubs.stream()
                .map(ClubMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClubResponseDto updateClub(UpdateClubRequestDto updateClubRequestDto) {
        // Check if the Club exists
        Club existingClub = clubRepository.findById(updateClubRequestDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", updateClubRequestDto.getId()));

        // Check if the Services exist
        Set<Long> serviceIds = updateClubRequestDto.getServiceIds();
        List<Service> services = serviceRepository.findAllById(serviceIds);

        Set<Long> foundServiceIds = services.stream().map(Service::getId).collect(Collectors.toSet());
        Set<Long> missingServiceIds = serviceIds.stream()
                .filter(serviceId -> !foundServiceIds.contains(serviceId))
                .collect(Collectors.toSet());

        if (!missingServiceIds.isEmpty()) {
            throw new ResourceNotFoundException("Service", "ids", missingServiceIds);
        }

        City city = cityRepository.findById(updateClubRequestDto.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City", "id", updateClubRequestDto.getCityId()));

        ClubMapper.updateEntity(existingClub, updateClubRequestDto, city, new HashSet<>(services));

        Club updatedClub = clubRepository.save(existingClub);
        return ClubMapper.toDto(updatedClub);
    }


    @Transactional
    public void deleteClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ServiceNotFoundException("Club not found with ID: " + clubId));

        // Remove the club from associated services
        for (Service service : club.getServices()) {
            service.getClubs().remove(club);
        }

        // Remove the club from associated tables
        tableRepository.deleteAll(club.getTables());

        // Clear the associations from the club side
        club.getServices().clear();
        club.getTables().clear();

        // Delete the club
        clubRepository.delete(club);
    }

    public List<ClubResponseDto> filterClubs(String name, Long cityId) {
        List<Club> clubs = clubRepository.filter(name, cityId);
        if (clubs.isEmpty()) {
            throw new ServiceNotFoundException("No clubs found");
        }
        return clubs.stream()
                .map(ClubMapper::toDto)
                .collect(Collectors.toList());
    }
}
