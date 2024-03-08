package org.example.bcm.core.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bcm.core.model.dto.request.ServiceRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateServiceRequestDto;
import org.example.bcm.core.model.dto.response.ServiceResponseDto;
import org.example.bcm.core.service.ServiceService;
import org.example.bcm.shared.Const.AppEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(AppEndpoint.SERVICE_ENDPOINT)
@RequiredArgsConstructor
public class ServiceController {


    private final ServiceService serviceService;

    @PostMapping
    public ResponseEntity<ServiceResponseDto> createService(@Valid @RequestBody ServiceRequestDto serviceRequestDto) {
        ServiceResponseDto createdService = serviceService.createService(serviceRequestDto);
        return new ResponseEntity<>(createdService, HttpStatus.CREATED);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceResponseDto> getServiceById(@PathVariable Long serviceId) {
        ServiceResponseDto service = serviceService.getServiceById(serviceId);
        return ResponseEntity.ok(service);
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponseDto>> getAllServices() {
        List<ServiceResponseDto> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @PutMapping
    public ResponseEntity<ServiceResponseDto> updateService(@Valid @RequestBody UpdateServiceRequestDto updateServiceRequestDto) {
        ServiceResponseDto updatedService = serviceService.updateService(updateServiceRequestDto);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Long serviceId) {
        serviceService.deleteService(serviceId);
        return ResponseEntity.noContent().build();
    }
}
