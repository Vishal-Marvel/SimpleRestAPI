package com.example.SampleRestApi.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentDTO {
    private String id = UUID.randomUUID().toString();
    private String feeId;
    private String paymentDate;
    private Integer amt;
}
