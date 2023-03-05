package com.example.SampleRestApi.Repository;


import com.example.SampleRestApi.models.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    List<Payment> findAllByFeeId(String fid);

}
