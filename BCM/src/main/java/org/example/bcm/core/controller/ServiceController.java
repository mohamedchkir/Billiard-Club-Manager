package org.example.bcm.core.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.bcm.core.model.dto.request.ServiceRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateServiceRequestDto;
import org.example.bcm.core.model.dto.response.ServiceResponseDto;
import org.example.bcm.core.service.S3Service;
import org.example.bcm.core.service.ServiceService;
import org.example.bcm.shared.Const.AppEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppEndpoint.SERVICE_ENDPOINT)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceController {


    private final ServiceService serviceService;
    private final S3Service s3Service;


    @PreAuthorize("hasAuthority('WRITE_SERVICE')")
    @PostMapping
    public ResponseEntity<ServiceResponseDto> createService(@Valid @ModelAttribute ServiceRequestDto serviceRequestDto) {
        String imageUrl = s3Service.uploadFile(serviceRequestDto.getFile());
        serviceRequestDto.setImageUrl(imageUrl);
        ServiceResponseDto createdService = serviceService.createService(serviceRequestDto);
        return new ResponseEntity<>(createdService, HttpStatus.CREATED);

    }

    @PreAuthorize("hasAuthority('READ_SERVICE')")
    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceResponseDto> getServiceById(@PathVariable Long serviceId) {
        ServiceResponseDto service = serviceService.getServiceById(serviceId);
        return ResponseEntity.ok(service);
    }

    @PreAuthorize("hasAuthority('READ_SERVICE')")
    @GetMapping
    public ResponseEntity<List<ServiceResponseDto>> getAllServices() {
        List<ServiceResponseDto> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @PreAuthorize("hasAuthority('UPDATE_SERVICE')")
    @PutMapping
    public ResponseEntity<ServiceResponseDto> updateService(@Valid @RequestBody UpdateServiceRequestDto updateServiceRequestDto) {
        ServiceResponseDto updatedService = serviceService.updateService(updateServiceRequestDto);
        return ResponseEntity.ok(updatedService);
    }

    @PreAuthorize("hasAuthority('DELETE_SERVICE')")
    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Long serviceId) {
        serviceService.deleteService(serviceId);
        return ResponseEntity.noContent().build();
    }
}
