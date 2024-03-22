package org.example.bcm.core.service;

import org.example.bcm.core.model.dto.response.CityResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CityService {
    List<CityResponseDto> getAllCities();

    void deleteCity(Long cityId);
}
