package org.example.bcm.core.model.mapper;

import org.example.bcm.core.model.dto.request.CityRequestDto;
import org.example.bcm.core.model.dto.response.CityResponseDto;
import org.example.bcm.core.model.entity.City;

public class CityMapper {

    public static City toEntity(CityRequestDto cityRequestDto) {
        City city = new City();
        city.setName(cityRequestDto.getName());
        return city;
    }

    public static CityResponseDto toDto(City city) {
        CityResponseDto dto = new CityResponseDto();
        dto.setId(city.getId());
        dto.setName(city.getName());
        return dto;
    }
}
