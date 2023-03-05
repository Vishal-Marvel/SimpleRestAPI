package com.example.SampleRestApi.Repository;

import com.example.SampleRestApi.models.Mark;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MarkRepository extends MongoRepository<Mark, String> {
    List<Mark> findAllByStudentId(String studentId);
}
