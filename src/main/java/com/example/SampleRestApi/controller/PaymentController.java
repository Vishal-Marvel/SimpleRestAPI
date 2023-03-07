package com.example.SampleRestApi.controller;

import com.example.SampleRestApi.DTO.PaymentDTO;
import com.example.SampleRestApi.Repository.PaymentRepository;
import com.example.SampleRestApi.models.Payment;
import com.example.SampleRestApi.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentService paymentService, PaymentRepository paymentRepository) {
        this.paymentService = paymentService;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/getFeePayments/{fid}")
    public List<PaymentDTO> getFeePayments(@PathVariable String fid){
        return paymentRepository.findAllByFeeId(fid)
                .stream()
                .map(paymentService::convertPaymentToDTO)
                .collect(Collectors.toList());
    }
    @PostMapping("/createPayment/{fid}")
    public PaymentDTO createPayment(@PathVariable String fid, @RequestBody Integer amt){
        Payment newPayment = new Payment();
        newPayment.setFeeId(fid);
        newPayment.setAmt(amt);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        newPayment.setPaymentDate(format.format(date));
        Payment save = paymentRepository.save(newPayment) ;
        return paymentService.updatePayment(save);

    }
    @PutMapping("/updatePayment")
    public PaymentDTO updatePayment(@RequestBody PaymentDTO paymentDTO){
        return paymentService
                .convertPaymentToDTO(paymentRepository
                        .save(paymentService
                                .convertDTOtoPayment(
                                paymentDTO)
                        )
                );
    }
    @DeleteMapping("/deletePayment/{pid}")
    public String deleteFee(@PathVariable String pid){
        paymentRepository.deleteById(pid);
        return "Deleted";
    }

}
