package org.example.bcm.core.model.mapper;

import org.example.bcm.core.model.dto.request.ClubRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateClubRequestDto;
import org.example.bcm.core.model.dto.response.ClubResponseDto;
import org.example.bcm.core.model.entity.City;
import org.example.bcm.core.model.entity.Club;
import org.example.bcm.core.model.entity.Service;

import java.util.Set;
import java.util.stream.Collectors;

public class ClubMapper {

    public static Club toEntity(ClubRequestDto requestDto, City city, Set<Service> services) {
        return Club.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .address(requestDto.getAddress())
                .openingHour(requestDto.getOpeningHour())
                .closeHour(requestDto.getCloseHour())
                .numberOfToken(requestDto.getNumberOfToken())
                .imageUrl(requestDto.getImageUrl())
                .city(city)
                .services(services)
                .build();
    }

    public static ClubResponseDto toDto(Club club) {
        return ClubResponseDto.builder()
                .id(club.getId())
                .name(club.getName())
                .address(club.getAddress())
                .description(club.getDescription())
                .openingHour(club.getOpeningHour())
                .closeHour(club.getCloseHour())
                .numberOfToken(club.getNumberOfToken())
                .imageUrl(club.getImageUrl())
                .city(CityMapper.toDto(club.getCity()))
                .services(club.getServices().stream()
                        .map(ServiceMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public static void updateEntity(Club existingClub, UpdateClubRequestDto updateDto, City city, Set<Service> services) {
        existingClub.setName(updateDto.getName());
        existingClub.setDescription(updateDto.getDescription());
        existingClub.setAddress(updateDto.getAddress());
        existingClub.setOpeningHour(updateDto.getOpeningHour());
        existingClub.setCloseHour(updateDto.getCloseHour());
        existingClub.setNumberOfToken(updateDto.getNumberOfToken());
        existingClub.setImageUrl(updateDto.getImageUrl());
        existingClub.setCity(city);
        existingClub.setServices(services);
    }
}
