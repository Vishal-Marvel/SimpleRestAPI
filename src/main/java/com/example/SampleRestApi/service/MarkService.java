package com.example.SampleRestApi.service;

import com.example.SampleRestApi.DTO.MarkDTO;
import com.example.SampleRestApi.models.Mark;
import com.example.SampleRestApi.models.Student;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;


@Service
public class MarkService {

    private final MongoTemplate mongoTemplate;

    public MarkService(MongoTemplate mongoTemplate) {

        this.mongoTemplate = mongoTemplate;
    }

    public Mark convertDTOtoMark(MarkDTO markDTO){
        Mark detached = new Mark();
        detached.setId(markDTO.getId());
        detached.setGrade(markDTO.getGrade());
        detached.setMarks(markDTO.getMarks());
        detached.setStudentId(markDTO.getStudentId());
        return detached;

    }
    public MarkDTO convertMarktoDTO(Mark mark){
        MarkDTO detached = new MarkDTO();
        detached.setId(mark.getId());
        detached.setGrade(mark.getGrade());
        detached.setMarks(mark.getMarks());
        detached.setStudentId(mark.getStudentId());
        return detached;
    }

    public MarkDTO updateMark(Mark mark){
        mongoTemplate.update(Student.class)
                .matching(where("id").is(mark.getStudentId()))
                .apply(new Update().push("markIds", mark.getId()))
                .first();
        return convertMarktoDTO(mark);
    }


}
