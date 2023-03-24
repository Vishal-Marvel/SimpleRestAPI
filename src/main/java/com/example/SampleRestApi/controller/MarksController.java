package com.example.SampleRestApi.controller;

import com.example.SampleRestApi.DTO.MarkDTO;
import com.example.SampleRestApi.Repository.MarkRepository;
import com.example.SampleRestApi.models.Mark;
import com.example.SampleRestApi.service.MarkService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mark")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "CRUD REST APIs for Student Marks Management")

public class MarksController {

    private final MarkService markService;
    private final MarkRepository markRepository;
    public MarksController( MarkService markService, MarkRepository markRepository) {
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

    @PostMapping("/createMarks/{sid}")
    public MarkDTO getStudentMarks(@PathVariable String sid, @RequestBody MarkDTO detachedMark) {
        Mark mark = new Mark();
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
