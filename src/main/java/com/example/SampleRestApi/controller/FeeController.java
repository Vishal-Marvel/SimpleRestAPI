package com.example.SampleRestApi.controller;

import com.example.SampleRestApi.DTO.FeeDTO;
import com.example.SampleRestApi.DTO.PaymentDTO;
import com.example.SampleRestApi.Repository.FeeRepository;
import com.example.SampleRestApi.Repository.PaymentRepository;
import com.example.SampleRestApi.models.Fee;
import com.example.SampleRestApi.service.FeeService;
import com.example.SampleRestApi.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FeeController {
    private final FeeService feeService;
    private final FeeRepository feeRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;

    public FeeController(FeeService feeService, FeeRepository feeRepository, PaymentRepository paymentRepository, PaymentService paymentService) {
        this.feeService = feeService;
        this.feeRepository = feeRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
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
    @GetMapping("/getStudentPayments/{sid}")
//    public List<PaymentDTO> getStudentPayments(@PathVariable String sid) {
//        ArrayList<List<String>> payments = new ArrayList<>();
//        List<Fee> studentFees = feeRepository.findAllByStudentId(sid);
//        for (Fee fee : studentFees) {
//            payments.add(fee.getPaymentIds());
//        }
//        ArrayList<String> paymentIds = new ArrayList<>();
//        for (List<String> feePayments : payments) {
//            if (feePayments != null) {
//                paymentIds.addAll(feePayments);
//            }
//        }
//        ArrayList<PaymentDTO> studentPayments = new ArrayList<>();
//        for (String id : paymentIds) {
//            Optional<Payment> payment = paymentRepository.findById(id);
//
//            studentPayments.add(paymentService.convertPaymentToDTO(payment.get()));
//        }
//
//        return studentPayments;
//    }
    public List<PaymentDTO> getStudentPayments(@PathVariable String sid) {
        return feeRepository.findAllByStudentId(sid).stream()
                .map(Fee::getPaymentIds)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .map(paymentRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(paymentService::convertPaymentToDTO)
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
