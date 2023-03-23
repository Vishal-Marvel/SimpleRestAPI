package com.example.SampleRestApi.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;
@Data
@Document(collection = "Students")
public class Student {
    public Student(){    }
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private Integer grade;
    private List<String> markIds;
    private List<String> feeIds;
}
