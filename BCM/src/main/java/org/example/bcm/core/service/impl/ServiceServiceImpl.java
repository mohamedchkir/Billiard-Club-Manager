package org.example.bcm.core.service.impl;

import org.example.bcm.core.model.dto.request.ServiceRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateServiceRequestDto;
import org.example.bcm.core.model.dto.response.ServiceResponseDto;
import org.example.bcm.core.service.ServiceService;

import java.util.List;

public class ServiceServiceImpl implements ServiceService {
    @Override
    public ServiceResponseDto createService(ServiceRequestDto serviceRequestDto) {
        return null;
    }

    @Override
    public ServiceResponseDto getServiceById(Long serviceId) {
        return null;
    }

    @Override
    public List<ServiceResponseDto> getAllServices() {
        return null;
    }

    @Override
    public ServiceResponseDto updateService(UpdateServiceRequestDto updateServiceRequestDto) {
        return null;
    }

    @Override
    public void deleteService(Long serviceId) {

    }
}
