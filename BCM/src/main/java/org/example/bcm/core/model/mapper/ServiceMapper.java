package org.example.bcm.core.model.mapper;

import org.example.bcm.core.model.dto.request.ServiceRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateServiceRequestDto;
import org.example.bcm.core.model.dto.response.ServiceResponseDto;
import org.example.bcm.core.model.entity.Service;

public class ServiceMapper {

    public static Service toEntity(ServiceRequestDto serviceRequestDto) {
        Service service = new Service();
        service.setName(serviceRequestDto.getName());
        service.setImageUrl(serviceRequestDto.getImageUrl());
        return service;
    }

    public static ServiceResponseDto toDto(Service service) {
        ServiceResponseDto dto = new ServiceResponseDto();
        dto.setId(service.getId());
        dto.setName(service.getName());
        dto.setImageUrl(service.getImageUrl());
        return dto;
    }

    public static void updateEntity(Service existingService, UpdateServiceRequestDto updateServiceRequestDto) {
        existingService.setName(updateServiceRequestDto.getName());
        existingService.setImageUrl(updateServiceRequestDto.getImageUrl());
    }
}
