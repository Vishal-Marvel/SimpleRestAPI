package com.example.SampleRestApi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JWTResponseDTO {
    private String accessToken;
    private final String tokenType = "Bearer";
}
