package com.zagvladimir.service.grade;

import com.zagvladimir.domain.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface GradeService {

  Page<Grade> findAll(Pageable page);

  Grade create(Grade grade, Long userId, Long itemId);

  Grade findById(Long gradeId);

  Grade update(Grade object);

  Long delete(Long gradeId);
}
