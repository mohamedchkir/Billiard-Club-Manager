package org.example.bcm.core.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bcm.core.model.dto.request.ServiceRequestDto;
import org.example.bcm.core.model.dto.response.ServiceResponseDto;
import org.example.bcm.core.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/services")
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

    @PutMapping("/{serviceId}")
    public ResponseEntity<ServiceResponseDto> updateService(@PathVariable Long serviceId, @Valid @RequestBody ServiceRequestDto serviceRequestDto) {
        ServiceResponseDto updatedService = serviceService.updateService(serviceId, serviceRequestDto);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Long serviceId) {
        serviceService.deleteService(serviceId);
        return ResponseEntity.noContent().build();
    }
}
