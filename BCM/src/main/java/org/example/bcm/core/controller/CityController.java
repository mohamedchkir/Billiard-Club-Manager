package org.example.bcm.core.controller;

import lombok.AllArgsConstructor;
import org.example.bcm.core.model.dto.response.CityResponseDto;
import org.example.bcm.core.service.CityService;
import org.example.bcm.shared.Const.AppEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppEndpoint.CITY_ENDPOINT)
@AllArgsConstructor(onConstructor = @__(@Autowired))

public class CityController {

    private final CityService cityService;

//    @PreAuthorize("hasAuthority('WRITE_CITY')")
    @GetMapping
    public ResponseEntity<List<CityResponseDto>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @PreAuthorize("hasAuthority('DELETE_CITY')")
    @DeleteMapping("/{cityId}")
    public ResponseEntity<Void> deleteCity( @PathVariable Long cityId) {
        cityService.deleteCity(cityId);
        return ResponseEntity.noContent().build();
    }

}
