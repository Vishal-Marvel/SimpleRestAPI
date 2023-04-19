package com.example.SampleRestApi.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;
import java.util.UUID;
@Data
@Document(collection = "Fees")
public class Fee {
    public Fee() {
    }

    @Id
    private String id = UUID.randomUUID().toString();
    private Integer grade, fee1, fee2, fee3, paidFee;
    private String studentId;
    private List<String> paymentIds;

}
