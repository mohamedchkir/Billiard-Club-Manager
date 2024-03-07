package org.example.bcm.core.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class AuthenticationResponseDto implements Serializable {
    @JsonProperty("access-token")
    private String accessToken;
    @JsonProperty("refresh-token")
    private String refreshToken;
    @JsonProperty("token-expiration")
    private Date tokenExpiration;
}
