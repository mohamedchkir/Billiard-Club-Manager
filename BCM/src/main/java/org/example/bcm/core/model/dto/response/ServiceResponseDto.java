package org.example.bcm.core.model.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ServiceResponseDto implements Serializable {
    private Long id;
    private String name;
    private String imageUrl;
}