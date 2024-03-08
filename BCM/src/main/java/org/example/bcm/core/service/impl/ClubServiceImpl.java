package org.example.bcm.core.service.impl;

import lombok.AllArgsConstructor;
import org.example.bcm.common.exception.ResourceNotFoundException;
import org.example.bcm.common.exception.ServiceNotFoundException;
import org.example.bcm.core.model.dto.request.ClubRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateClubRequestDto;
import org.example.bcm.core.model.dto.response.ClubResponseDto;
import org.example.bcm.core.model.entity.City;
import org.example.bcm.core.model.entity.Club;
import org.example.bcm.core.model.entity.Service;
import org.example.bcm.core.repository.CityRepository;
import org.example.bcm.core.repository.ClubRepository;
import org.example.bcm.core.repository.ServiceRepository;
import org.example.bcm.core.service.ClubService;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final CityRepository cityRepository;
    private final ServiceRepository serviceRepository;

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


        Club club = modelMapper.map(clubRequestDto, Club.class);
        club.setCity(city);
        club.setServices(new HashSet<>(services));

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
        if (clubs.isEmpty()) {
            throw new ServiceNotFoundException("No clubs found");
        }
        return clubs.stream()
                .map(club -> modelMapper.map(club, ClubResponseDto.class))
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

        if (services.size() != serviceIds.size()) {
            Set<Long> foundServiceIds = services.stream().map(Service::getId).collect(Collectors.toSet());
            Set<Long> missingServiceIds = new HashSet<>(serviceIds);
            missingServiceIds.removeAll(foundServiceIds);

            throw new ResourceNotFoundException("Service", "ids", missingServiceIds);
        }


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
