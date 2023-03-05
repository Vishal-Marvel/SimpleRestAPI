package com.example.SampleRestApi.controller;

import com.example.SampleRestApi.DTO.MarkDTO;
import com.example.SampleRestApi.Repository.MarkRepository;
import com.example.SampleRestApi.Repository.StudentRepository;
import com.example.SampleRestApi.models.Mark;
import com.example.SampleRestApi.models.Student;
import com.example.SampleRestApi.service.MarkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MarksController {

    private final StudentRepository studentRepository;
    private final MarkService markService;
    private final MarkRepository markRepository;
    public MarksController(StudentRepository studentRepository, MarkService markService, MarkRepository markRepository) {
        this.studentRepository = studentRepository;
        this.markService = markService;
        this.markRepository = markRepository;
    }

    @GetMapping("/getMarks")
    public List<MarkDTO> getMarks(){
        return markRepository.findAll()
                .stream()
                .map(markService::convertMarktoDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/setMarks/{sid}")
    public MarkDTO getStudentMarks(@PathVariable String sid, @RequestBody MarkDTO detachedMark) {
        Mark mark = new Mark();
        Optional<Student> student = studentRepository.findById(sid);
        mark.setMarks(detachedMark.getMarks());
        mark.setGrade(detachedMark.getGrade());
        mark.setStudentId(sid);
        Mark saved = markRepository.save(mark);
        return markService.updateMark(saved);
    }

//    @GetMapping
    @DeleteMapping("/deleteMarks/{id}")
    public String deleteMarks(@PathVariable String id){
        markRepository.deleteById(id);
        return "Deleted";
    }
}
