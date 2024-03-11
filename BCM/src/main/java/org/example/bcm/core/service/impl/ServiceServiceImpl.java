package org.example.bcm.core.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.bcm.common.exception.ServiceNotFoundException;
import org.example.bcm.core.model.dto.request.ServiceRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateServiceRequestDto;
import org.example.bcm.core.model.dto.response.ServiceResponseDto;
import org.example.bcm.core.model.entity.Club;
import org.example.bcm.core.model.entity.Service;
import org.example.bcm.core.model.mapper.ServiceMapper; // Import your custom mapper
import org.example.bcm.core.repository.ServiceRepository;
import org.example.bcm.core.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    @Override
    public ServiceResponseDto createService(ServiceRequestDto serviceRequestDto) {
        Service service = ServiceMapper.toEntity(serviceRequestDto);
        Service savedService = serviceRepository.save(service);
        return ServiceMapper.toDto(savedService);
    }

    @Override
    public ServiceResponseDto getServiceById(Long serviceId) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found with ID: " + serviceId));
        return ServiceMapper.toDto(service);
    }

    @Override
    public List<ServiceResponseDto> getAllServices() {
        List<Service> services = serviceRepository.findAll();
        if (services.isEmpty()) {
            throw new ServiceNotFoundException("No services found");
        }
        return services.stream()
                .map(ServiceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceResponseDto updateService(UpdateServiceRequestDto updateServiceRequestDto) {
        Service existingService = serviceRepository.findById(updateServiceRequestDto.getId())
                .orElseThrow(() -> new ServiceNotFoundException("Service not found with ID: " + updateServiceRequestDto.getId()));

        ServiceMapper.updateEntity(existingService, updateServiceRequestDto);
        Service updatedService = serviceRepository.save(existingService);
        return ServiceMapper.toDto(updatedService);
    }

    @Transactional
    public void deleteService(Long serviceId) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found with ID: " + serviceId));

        // Remove the service from associated clubs
        for (Club club : service.getClubs()) {
            club.getServices().remove(service);
        }

        // Clear the association from the service side
        service.getClubs().clear();

        // Delete the service
        serviceRepository.delete(service);
    }
}
