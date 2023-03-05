package com.example.SampleRestApi.service;

import com.example.SampleRestApi.DTO.StudentDTO;
import com.example.SampleRestApi.Repository.StudentRepository;
import com.example.SampleRestApi.models.Student;
import org.springframework.stereotype.Service;



@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student convertDTOtoStudent(StudentDTO studentDTO){
        Student detached = new Student();
        detached.setId(studentDTO.getId());
        detached.setName(studentDTO.getName());
        detached.setGrade(studentDTO.getGrade());
        detached.setMarkIds(studentDTO.getMarkIds());
        return detached;

    }
    public StudentDTO convertStudentToDTO(Student student){
        StudentDTO detached = new StudentDTO();
        detached.setId(student.getId());
        detached.setName(student.getName());
        detached.setGrade(student.getGrade());
        detached.setMarkIds(student.getMarkIds());
        return detached;
    }


}
