package com.zagvladimir.service.grade;

import com.zagvladimir.dto.requests.grade.GradeCreateRequest;
import com.zagvladimir.dto.response.GradeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface GradeService {

  Page<GradeResponse> findAll(Pageable page);

  GradeResponse create(GradeCreateRequest request);

  GradeResponse findById(Long gradeId);

  Long delete(Long gradeId);

  Long softDelete(Long gradeId);

  String getLoginWhoRatedByGradeId(Long id);
}
