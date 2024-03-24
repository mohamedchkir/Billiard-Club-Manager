package org.example.bcm.core.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ServiceRequestDto {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    private String imageUrl;
    private MultipartFile file;

}
