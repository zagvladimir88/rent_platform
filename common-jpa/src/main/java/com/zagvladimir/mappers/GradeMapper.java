package com.zagvladimir.mappers;

import com.zagvladimir.domain.Grade;
import com.zagvladimir.dto.requests.grade.GradeCreateRequest;
import com.zagvladimir.dto.response.GradeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GradeMapper {

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "user.id", target = "userId")
    GradeResponse toResponse(Grade grade);

    Grade convertCreateRequest(GradeCreateRequest request);
}
