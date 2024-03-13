package org.example.bcm.core.service;

import org.example.bcm.core.model.dto.request.ServiceRequestDto;
import org.example.bcm.core.model.dto.request.update.UpdateServiceRequestDto;
import org.example.bcm.core.model.dto.response.ServiceResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceService {
    ServiceResponseDto createService(ServiceRequestDto serviceRequestDto);

    ServiceResponseDto getServiceById(Long serviceId);

    List<ServiceResponseDto> getAllServices();

    ServiceResponseDto updateService(UpdateServiceRequestDto updateServiceRequestDto);

    void deleteService(Long serviceId);

}
