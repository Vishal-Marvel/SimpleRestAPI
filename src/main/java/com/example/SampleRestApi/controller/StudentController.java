package com.example.SampleRestApi.controller;

import com.example.SampleRestApi.DTO.MarkDTO;
import com.example.SampleRestApi.DTO.StudentDTO;
import com.example.SampleRestApi.Exceptions.UserNotFoundException;
import com.example.SampleRestApi.Repository.MarkRepository;
import com.example.SampleRestApi.Repository.StudentRepository;
import com.example.SampleRestApi.models.Student;
import com.example.SampleRestApi.security.AuthService;
import com.example.SampleRestApi.service.MarkService;
import com.example.SampleRestApi.service.StudentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
@Tag(name = "CRUD REST APIs for Student Management")

public class StudentController {
    private final StudentRepository studentRepository;
    private final MarkService markService;
    private final MarkRepository markRepository;
    private final StudentService studentService;
    private final AuthService authService;

    public StudentController(StudentRepository studentRepository, MarkService markService, MarkRepository markRepository, StudentService studentService, AuthService authService) {
        this.studentRepository = studentRepository;
        this.markService = markService;
        this.markRepository = markRepository;
        this.studentService = studentService;
        this.authService = authService;
    }

    @GetMapping( "/students")
    public String getStudents(Model model){

        List<StudentDTO> studentDTOList =  studentRepository.findAll()
                .stream()
                .map(studentService::convertStudentToDTO)
                .toList(); // equivalent of SELECT * from Students; db.Students.find();
        model.addAttribute("students", studentDTOList);
        return "student/Students";
    }


    @GetMapping("/getStudent/{id}")
    public String getStudentById(@PathVariable String id, Model model){

        Student result = studentRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Student with id : " + id + " Not Found"));
        model.addAttribute("student", studentService.convertStudentToDTO(result));
        return "student/student";
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

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/updateStudent/{id}")
    public String updateStudent(HttpServletRequest request, @PathVariable String id, Model model){
        String auth =  authService.chkAuth(request);
        if (auth=="true") {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("Student Not Found"));
            model.addAttribute("student", student);
            return "student/editStudent";
        }
        else {
            return auth;
        }

    }

    @GetMapping("/newStudent")
    @PreAuthorize("hasRole('USER')")
    public String newStudent(Model model){
//        String auth =  authService.chkAuth(request);
//        if (auth=="true") {
            StudentDTO studentDTO = new StudentDTO();
            model.addAttribute("student", studentDTO);
            return "student/editStudent";
//        }
//        else {
//            return auth;
//        }
    }

    @PostMapping("/createOrEditStudent")
    @SecurityRequirement(name = "Bearer Authentication")
    public String createOrEditStudent(@ModelAttribute StudentDTO studentDTO){ //StudentDTO will contain Name and GRADE alone
        Student createdstudent = new Student();
        createdstudent.setName(studentDTO.getName());
        createdstudent.setGrade(studentDTO.getGrade());
        studentRepository.save(createdstudent);
        return "redirect:/students";
    }
    @DeleteMapping("/deleteStudent/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER')")
    public String deleteStudent(@PathVariable String id){
        studentRepository.deleteById(id);
        return "Deleted";
    }
}
