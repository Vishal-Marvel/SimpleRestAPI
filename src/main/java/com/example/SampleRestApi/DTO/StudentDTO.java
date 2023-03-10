package com.example.SampleRestApi.DTO;

import lombok.Data;

import java.util.List;
import java.util.UUID;
@Data
public class StudentDTO {
    private String name;
    private String id = UUID.randomUUID().toString();
    private Integer grade;
    private List<String> markIds;
    private List<String> feeIds;
}
