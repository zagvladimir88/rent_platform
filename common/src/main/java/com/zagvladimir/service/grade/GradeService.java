package com.zagvladimir.service.grade;

import com.zagvladimir.domain.Grade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface GradeService {

    List<Grade> findAll();

    Grade create(Grade grade, Long userId, Long itemId);

    Optional<Grade> findById(Long gradeId);

    Grade update(Grade object);

    Long delete(Long gradeId);

    List<Grade> search(int limit, int offset);
}
