package com.example.SampleRestApi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "Marks")
public class Mark {

    @Id
    private String id = UUID.randomUUID().toString();
    private Integer grade;
    private String studentId;
    private List<Integer> marks;
}
