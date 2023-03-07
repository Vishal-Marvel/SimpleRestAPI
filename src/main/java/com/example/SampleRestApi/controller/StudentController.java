package com.example.SampleRestApi.controller;

import com.example.SampleRestApi.DTO.MarkDTO;
import com.example.SampleRestApi.DTO.StudentDTO;
import com.example.SampleRestApi.Repository.MarkRepository;
import com.example.SampleRestApi.Repository.StudentRepository;
import com.example.SampleRestApi.models.Student;
import com.example.SampleRestApi.service.MarkService;
import com.example.SampleRestApi.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class StudentController {
    private final StudentRepository studentRepository;
    private final MarkService markService;
    private final MarkRepository markRepository;
    private final StudentService studentService;

    public StudentController(StudentRepository studentRepository, MarkService markService, MarkRepository markRepository, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.markService = markService;
        this.markRepository = markRepository;
        this.studentService = studentService;
    }

    //Get Mapping
    @GetMapping("/getStudents")
        public List<StudentDTO> getStudents(){
        return studentRepository.findAll()
                .stream()
                .map(studentService::convertStudentToDTO)
                .collect(Collectors.toList()); // equivalent of SELECT * from Students; db.Students.find();
    }

    @GetMapping("/getStudent/{id}")
    public StudentDTO getStudentById(@PathVariable String id){

        Optional<Student> result = studentRepository.findById(id);
        if (result.isPresent()) {
            return studentService.convertStudentToDTO(result.get());
        }
        else {
            return studentService.convertStudentToDTO(new Student(id));
        }

    }

    @GetMapping("/getStudents/{grade}")
    public List<StudentDTO> getStudentByGrade(@PathVariable Integer grade){
        return studentRepository.findAllByGrade(grade)
                .stream()
                .map(studentService::convertStudentToDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/getStudentsbyName/{name}")
    public List<StudentDTO> getStudentByName(@PathVariable String name){
        return studentRepository.findAllByName(name)
                .stream()
                .map(studentService::convertStudentToDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/getStudentMarks/{id}")
    public List<MarkDTO> getStudentMarks(@PathVariable String id){
        return markRepository.findAllByStudentId(id)
                .stream()
                .map(markService::convertMarktoDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/updateStudent")
    public StudentDTO updateStudent(@RequestBody StudentDTO studentDTO){
        Student detachedStudent = studentService.convertDTOtoStudent(studentDTO);
        Student updatedStudent = studentRepository.save(detachedStudent);
        return  studentService.convertStudentToDTO(updatedStudent);
    }

    @PostMapping("/createStudent")
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO){ //StudentDTO will contain Name and GRADE alone
        Student createdstudent = new Student();
        createdstudent.setName(studentDTO.getName());
        createdstudent.setGrade(studentDTO.getGrade());
        Student savedstudent = studentRepository.save(createdstudent);
        return studentService.convertStudentToDTO(savedstudent);
    }

    @DeleteMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable String id){
        studentRepository.deleteById(id);
        return "Deleted";
    }
}
