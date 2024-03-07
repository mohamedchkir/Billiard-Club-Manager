package org.example.bcm.core.model.dto.response;


import org.example.bcm.core.model.entity.Club;

import java.io.Serializable;
import java.util.Set;

public class ServiceResponseDto implements Serializable {
    private Long id;
    private String name;
    private String imageUrl;
    private Set<ClubResponseDto> clubs;
}