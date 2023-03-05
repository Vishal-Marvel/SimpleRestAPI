package com.example.SampleRestApi.service;

import com.example.SampleRestApi.DTO.PaymentDTO;
import com.example.SampleRestApi.models.Fee;
import com.example.SampleRestApi.models.Payment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class PaymentService {

    private final MongoTemplate mongoTemplate;

    public PaymentService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public Payment convertDTOtoPayment(PaymentDTO paymentDTO){
        Payment detached = new Payment();
        detached.setId(paymentDTO.getId());
        detached.setFeeId(paymentDTO.getFeeId());
        detached.setPaymentDate(paymentDTO.getPaymentDate());
        detached.setAmt(paymentDTO.getAmt());
        return detached;

    }
    public PaymentDTO convertPaymentToDTO(Payment payment){
        PaymentDTO detached = new PaymentDTO();
        detached.setId(payment.getId());
        detached.setFeeId(payment.getFeeId());
        detached.setPaymentDate(payment.getPaymentDate());
        detached.setAmt(payment.getAmt());
        return detached;
    }

    public PaymentDTO updatePayment(Payment payment){
        mongoTemplate.update(Fee.class)
                .matching(where("id").is(payment.getFeeId()))
                .apply(new Update().push("paymentIds", payment.getId()))
                .first();
        return convertPaymentToDTO(payment);
    }

}
