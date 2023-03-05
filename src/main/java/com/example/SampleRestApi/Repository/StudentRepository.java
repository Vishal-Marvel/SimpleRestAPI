package com.example.SampleRestApi.Repository;

import com.example.SampleRestApi.models.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {

    List<Student> findAllByGrade(Integer grade);

    List<Student> findAllByName(String name);


}
