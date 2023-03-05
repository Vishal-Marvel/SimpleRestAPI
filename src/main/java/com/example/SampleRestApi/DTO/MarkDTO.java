package com.example.SampleRestApi.DTO;

import com.example.SampleRestApi.models.Student;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MarkDTO {
    private String id = UUID.randomUUID().toString();
    private Integer grade;
    private String studentId;
    private List<Integer> marks;
}
