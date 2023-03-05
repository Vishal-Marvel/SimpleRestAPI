package com.example.SampleRestApi.service;

import com.example.SampleRestApi.DTO.StudentDTO;
import com.example.SampleRestApi.models.Student;
import org.springframework.stereotype.Service;



@Service
public class StudentService {

    public Student convertDTOtoStudent(StudentDTO studentDTO){
        Student detached = new Student();
        detached.setId(studentDTO.getId());
        detached.setName(studentDTO.getName());
        detached.setGrade(studentDTO.getGrade());
        detached.setMarkIds(studentDTO.getMarkIds());
        detached.setFeeIds(studentDTO.getFeeIds());
        return detached;

    }
    public StudentDTO convertStudentToDTO(Student student){
        StudentDTO detached = new StudentDTO();
        detached.setId(student.getId());
        detached.setName(student.getName());
        detached.setGrade(student.getGrade());
        detached.setMarkIds(student.getMarkIds());
        detached.setFeeIds(student.getFeeIds());
        return detached;
    }


}
