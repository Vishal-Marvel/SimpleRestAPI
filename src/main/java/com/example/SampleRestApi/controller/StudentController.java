package com.example.SampleRestApi.controller;

import com.example.SampleRestApi.DTO.MarkDTO;
import com.example.SampleRestApi.DTO.StudentDTO;
import com.example.SampleRestApi.Exceptions.UserNotFoundException;
import com.example.SampleRestApi.Repository.MarkRepository;
import com.example.SampleRestApi.Repository.StudentRepository;
import com.example.SampleRestApi.models.Student;
import com.example.SampleRestApi.service.MarkService;
import com.example.SampleRestApi.service.StudentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "Basic Authentication")
@RequestMapping("/student")
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

    @GetMapping("")
        public List<StudentDTO> getStudents(){

        return studentRepository.findAll()
                .stream()
                .map(studentService::convertStudentToDTO)
                .collect(Collectors.toList()); // equivalent of SELECT * from Students; db.Students.find();
    }
//    public ModelAndView getStudents(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("index");
//        List<StudentDTO> studentDTOS = studentRepository.findAll()
//                        .stream().map(studentService::convertStudentToDTO)
//                        .collect(Collectors.toList());
//        modelAndView.addObject("students", studentDTOS);
//        return modelAndView;
//    }

    @GetMapping("/getStudent/{id}")
    public StudentDTO getStudentById(@PathVariable String id){

        Student result = studentRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Student with id : " + id + " Not Found"));
        return studentService.convertStudentToDTO(result);
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
    @SecurityRequirement(name = "Basic Authentication")
    @PutMapping("/updateStudent/{id}")
    public StudentDTO updateStudent(@RequestBody StudentDTO studentDTO, @PathVariable String id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Student Not Found"));
        student.setName(studentDTO.getName());
        student.setGrade(studentDTO.getGrade());

        Student updatedStudent = studentRepository.save(student);
        return  studentService.convertStudentToDTO(updatedStudent);
    }

    @SecurityRequirement(name = "Basic Authentication")
    @PostMapping("/createStudent")
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO){ //StudentDTO will contain Name and GRADE alone
        Student createdstudent = new Student();
        createdstudent.setName(studentDTO.getName());
        createdstudent.setGrade(studentDTO.getGrade());
        Student savedstudent = studentRepository.save(createdstudent);
        return studentService.convertStudentToDTO(savedstudent);
    }
    @SecurityRequirement(name = "Basic Authentication")
    @DeleteMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable String id){
        studentRepository.deleteById(id);
        return "Deleted";
    }
}
