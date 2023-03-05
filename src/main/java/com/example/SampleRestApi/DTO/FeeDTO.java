package com.example.SampleRestApi.DTO;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class FeeDTO {
    private String id = UUID.randomUUID().toString();
    private Integer grade, fee1, fee2, fee3, paidFee;
    private String studentId;
    private List<String> paymentIds;
}
