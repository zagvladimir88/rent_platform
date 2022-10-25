package com.zagvladimir.controller.mappers;

import com.zagvladimir.controller.requests.grade.GradeCreateRequest;
import com.zagvladimir.controller.response.GradeResponse;
import com.zagvladimir.domain.Grade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GradeMapper {

    @Mapping(source = "itemLeased.id", target = "itemLeasedId")
    @Mapping(source = "userFrom.id", target = "userFromId")
    @Mapping(source = "userTo.id", target = "userToId")
    GradeResponse toResponse(Grade grade);

    Grade convertCreateRequest(GradeCreateRequest request);
}
