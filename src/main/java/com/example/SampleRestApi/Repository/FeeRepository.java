package com.example.SampleRestApi.Repository;

import com.example.SampleRestApi.models.Fee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FeeRepository extends MongoRepository<Fee, String> {
    List<Fee> findAllByStudentId(String sid);



}
