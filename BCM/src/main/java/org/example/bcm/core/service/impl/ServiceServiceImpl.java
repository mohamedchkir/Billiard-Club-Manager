package org.example.bcm.core.service.impl;

import lombok.AllArgsConstructor;
import org.example.bcm.common.exception.ServiceNotFoundException;
import org.example.bcm.core.model.dto.request.ServiceRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateServiceRequestDto;
import org.example.bcm.core.model.dto.response.ServiceResponseDto;
import org.example.bcm.core.repository.ServiceRepository;
import org.example.bcm.core.service.ServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.bcm.core.model.entity.Service;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceServiceImpl implements ServiceService {


    private ServiceRepository serviceRepository;


    private ModelMapper modelMapper;

    @Override
    public ServiceResponseDto createService(ServiceRequestDto serviceRequestDto) {
        Service service = modelMapper.map(serviceRequestDto, Service.class);
        Service savedService = serviceRepository.save(service);
        return modelMapper.map(savedService, ServiceResponseDto.class);
    }

    @Override
    public ServiceResponseDto getServiceById(Long serviceId) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found with ID: " + serviceId));
        return modelMapper.map(service, ServiceResponseDto.class);
    }

    @Override
    public List<ServiceResponseDto> getAllServices() {
        List<Service> services = serviceRepository.findAll();
        return services.stream()
                .map(service -> modelMapper.map(service, ServiceResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ServiceResponseDto updateService(UpdateServiceRequestDto updateServiceRequestDto) {
        Service existingService = serviceRepository.findById(updateServiceRequestDto.getId())
                .orElseThrow(() -> new ServiceNotFoundException("Service not found with ID: " + updateServiceRequestDto.getId()));

        modelMapper.map(updateServiceRequestDto, existingService);
        Service updatedService = serviceRepository.save(existingService);
        return modelMapper.map(updatedService, ServiceResponseDto.class);
    }

    @Override
    public void deleteService(Long serviceId) {
        if (!serviceRepository.existsById(serviceId)) {
            throw new ServiceNotFoundException("Service not found with ID: " + serviceId);
        }
        serviceRepository.deleteById(serviceId);
    }
}
