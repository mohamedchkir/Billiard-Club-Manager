    package org.example.bcm.core.model.dto.request.update;

    import jakarta.validation.constraints.*;
    import lombok.Builder;
    import lombok.Data;
    import org.springframework.web.multipart.MultipartFile;

    import java.time.LocalTime;
    import java.util.Set;

    @Data
    @Builder
    public class UpdateClubRequestDto {
        @NotNull(message = "Id is required for update")
        private Long id;
        @NotBlank(message = "Name is required")
        private String name;

        private String description;

        @NotBlank(message = "Address is required")
        private String address;

        @NotNull(message = "Opening hour is required")
        private LocalTime openingHour;

        @NotNull(message = "Close hour is required")
        private LocalTime closeHour;

        private Integer numberOfToken;

        private String imageUrl;

        private MultipartFile file;


        @NotNull(message = "City ID is required")
        private Long cityId;

        @NotNull(message = "Services are required")
        @Size(min = 1, message = "At least one service is required")
        private Set<Long> serviceIds;
    }
