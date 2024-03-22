package org.example.bcm.core.service.impl;

import lombok.AllArgsConstructor;
import org.example.bcm.common.exception.ServiceNotFoundException;
import org.example.bcm.core.model.dto.response.CityResponseDto;
import org.example.bcm.core.model.entity.City;
import org.example.bcm.core.model.mapper.CityMapper;
import org.example.bcm.core.repository.CityRepository;
import org.example.bcm.core.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public List<CityResponseDto> getAllCities() {
        List<City> cities = cityRepository.findAll();
        if (cities.isEmpty()) {
            throw new ServiceNotFoundException("No cities found");
        }
        return cities.stream()
                .map(CityMapper::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public void deleteCity(Long cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new ServiceNotFoundException("City not found"));
        cityRepository.delete(city);
    }
}
