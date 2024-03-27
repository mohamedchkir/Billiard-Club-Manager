package org.example.bcm.core.model.dto.request.update;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserImageAndNumberOfToken {

    private String imageUrl;
    private Integer numberOfToken;
}
