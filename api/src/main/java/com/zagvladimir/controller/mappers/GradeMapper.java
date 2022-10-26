package com.zagvladimir.controller.mappers;

import com.zagvladimir.controller.requests.grade.GradeCreateRequest;
import com.zagvladimir.controller.response.GradeResponse;
import com.zagvladimir.domain.Grade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GradeMapper {

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "user.id", target = "userId")
    GradeResponse toResponse(Grade grade);

    Grade convertCreateRequest(GradeCreateRequest request);
}
