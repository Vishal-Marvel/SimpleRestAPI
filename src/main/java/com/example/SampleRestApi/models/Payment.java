package com.example.SampleRestApi.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "Payments")
@Data
public class Payment {
    @Id
    private String id = UUID.randomUUID().toString();
    private String feeId;
    private String paymentDate;
    private Integer amt;



}
