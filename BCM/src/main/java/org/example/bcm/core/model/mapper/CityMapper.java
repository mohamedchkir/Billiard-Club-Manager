package org.example.bcm.core.model.mapper;

import org.example.bcm.core.model.dto.request.CityRequestDto;
import org.example.bcm.core.model.dto.response.CityResponseDto;
import org.example.bcm.core.model.entity.City;

public class CityMapper {

    public static City toEntity(CityRequestDto cityRequestDto) {
        return City.builder()
                .name(cityRequestDto.getName())
                .build();
    }

    public static CityResponseDto toDto(City city) {
        return CityResponseDto.builder()
                .id(city.getId())
                .name(city.getName())
                .build();
    }
}
