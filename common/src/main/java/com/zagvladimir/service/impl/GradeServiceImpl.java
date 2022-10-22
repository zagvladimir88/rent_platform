package com.zagvladimir.service.impl;

import com.zagvladimir.domain.Grade;
import com.zagvladimir.repository.GradeRepository;
import com.zagvladimir.service.GradeService;
import com.zagvladimir.service.ItemLeasedService;
import com.zagvladimir.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final ItemLeasedService itemLeasedService;
    private final UserService userService;

    @Override
    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    @Override
    public Grade create(Grade grade, Long userToId, Long userFromId, Long itemLeasedId) {
        grade.setUserTo(userService.findById(userToId));
        grade.setUserFrom(userService.findById(userFromId));
        grade.setItemLeased(itemLeasedService.findById(itemLeasedId));
        return gradeRepository.save(grade);
    }

    @Override
    public Optional<Grade> findById(Long gradeId) {
        return gradeRepository.findById(gradeId);
    }

    @Override
    public Grade update(Grade object) {
        return null;
    }

    @Override
    public Long delete(Long gradeId) {
        gradeRepository.deleteById(gradeId);
        return gradeId;
    }

    @Override
    public List<Grade> search(int limit, int offset) {
        return null;
    }
}
