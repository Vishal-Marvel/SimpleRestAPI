package com.example.SampleRestApi.controller;

import com.example.SampleRestApi.DTO.FeeDTO;
import com.example.SampleRestApi.Repository.FeeRepository;
import com.example.SampleRestApi.models.Fee;
import com.example.SampleRestApi.service.FeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FeeController {
    private final FeeService feeService;
    private final FeeRepository feeRepository;

    public FeeController(FeeService feeService, FeeRepository feeRepository) {
        this.feeService = feeService;
        this.feeRepository = feeRepository;
    }
    @GetMapping("/getAllFees")
    public List<FeeDTO> getAllFee(){
        return feeRepository.findAll()
                .stream()
                .map(feeService::convertFeetoDTO)
                .collect(Collectors.toList());
    }
    @GetMapping("/getFees/{sid}")
    public List<FeeDTO> getFee(@PathVariable String sid){
       return feeRepository.findAllByStudentId(sid)
               .stream()
               .map(feeService::convertFeetoDTO)
               .collect(Collectors.toList());
    }
    @PostMapping("/setFee/{sid}")
    public FeeDTO getFee(@PathVariable String sid, @RequestBody FeeDTO feeDTO){
        Fee newFee = feeService.convertDTOtoFee(feeDTO);
        newFee.setStudentId(sid);
        Fee savedFee = feeRepository.save(newFee);
        return feeService.updateFee(savedFee);
    }
    @DeleteMapping("/deleteFee/{fid}")
    public String deleteFee(@PathVariable String fid){
        feeRepository.deleteById(fid);
        return "Deleted";
    }
}
