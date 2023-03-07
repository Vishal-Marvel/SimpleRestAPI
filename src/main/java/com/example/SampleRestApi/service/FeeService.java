package com.example.SampleRestApi.service;

import com.example.SampleRestApi.DTO.FeeDTO;
import com.example.SampleRestApi.models.Fee;
import com.example.SampleRestApi.models.Student;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;


@Service
public class FeeService {

    private final MongoTemplate mongoTemplate;
    public FeeService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Fee convertDTOtoFee(FeeDTO feeDTO){
        Fee detached = new Fee();
        detached.setId(feeDTO.getId());
        detached.setGrade(feeDTO.getGrade());
        detached.setFee1(feeDTO.getFee1());
        detached.setFee2(feeDTO.getFee2());
        detached.setFee3(feeDTO.getFee3());
        detached.setPaidFee(feeDTO.getPaidFee());
        detached.setStudentId(feeDTO.getStudentId());
        detached.setPaymentIds(feeDTO.getPaymentIds());
        return detached;

    }
    public FeeDTO convertFeeToDTO(Fee fee){
        FeeDTO detached = new FeeDTO();
        detached.setId(fee.getId());
        detached.setGrade(fee.getGrade());
        detached.setFee1(fee.getFee1());
        detached.setFee2(fee.getFee2());
        detached.setFee3(fee.getFee3());
        detached.setPaidFee(fee.getPaidFee());
        detached.setStudentId(fee.getStudentId());
        detached.setPaymentIds(fee.getPaymentIds());
        return detached;

    }


    public FeeDTO updateFee(Fee fee){
        mongoTemplate.update(Student.class)
                .matching(where("id").is(fee.getStudentId()))
                .apply(new Update().push("feeIds", fee.getId()))
                .first();
        return convertFeeToDTO(fee);
    }


}
