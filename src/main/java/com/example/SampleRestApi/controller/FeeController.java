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
                .map(feeService::convertFeeToDTO)
                .collect(Collectors.toList());
    }
    @GetMapping("/getStudentFees/{sid}")
    public List<FeeDTO> getStudentFees(@PathVariable String sid){
       return feeRepository.findAllByStudentId(sid)
               .stream()
               .map(feeService::convertFeeToDTO)
               .collect(Collectors.toList());
    }

    @GetMapping("/getFee/{fid}")
    public FeeDTO getFee(@PathVariable String fid){
        Optional<Fee> fee = feeRepository.findById(fid);
        return feeService.convertFeeToDTO(fee.get());
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

    @PutMapping("/updateFee")
    public FeeDTO updateFee(@RequestBody FeeDTO feeDTO){
        return feeService.convertFeeToDTO(feeRepository.save(feeService.convertDTOtoFee(feeDTO)));
    }

    @PostMapping("/createFee/{sid}")
    public FeeDTO getFee(@PathVariable String sid, @RequestBody FeeDTO feeDTO){
        Fee newFee = feeService.convertDTOtoFee(feeDTO);
        newFee.setStudentId(sid);
        newFee.setPaidFee(0);
        Fee savedFee = feeRepository.save(newFee);
        return feeService.updateFee(savedFee);
    }
    @DeleteMapping("/deleteFee/{fid}")
    public String deleteFee(@PathVariable String fid){
        feeRepository.deleteById(fid);
        return "Deleted";
    }
}
