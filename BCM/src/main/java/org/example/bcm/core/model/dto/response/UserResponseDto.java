package org.example.bcm.core.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class UserResponseDto implements Serializable {
    private String firstName;
    private String lastName;
    private Integer numberOfToken;
    private String telephone;
    private String email;
    private String password;
    private String role;
    private List<String> permissions;
}