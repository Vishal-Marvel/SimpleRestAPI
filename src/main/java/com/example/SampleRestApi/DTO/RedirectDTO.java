package com.example.SampleRestApi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RedirectDTO {
    private boolean alert;
    private String msg;
    private String token;
}
