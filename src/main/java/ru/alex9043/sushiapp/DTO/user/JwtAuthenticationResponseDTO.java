package ru.alex9043.sushiapp.DTO.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthenticationResponseDTO {
    private String accessToken;
    private String refreshToken;
}
