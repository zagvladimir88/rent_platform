package com.zagvladimir.service.grade;

import com.zagvladimir.domain.Grade;
import com.zagvladimir.repository.GradeRepository;
import com.zagvladimir.service.item.ItemService;
import com.zagvladimir.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final ItemService itemService;
    private final UserService userService;

    @Override
    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    @Transactional
    @Override
    public Grade create(Grade grade, Long userId, Long itemId) {
        grade.setUser(userService.findById(userId));
        grade.setItem(itemService.findById(itemId));
        return gradeRepository.save(grade);
    }

    @Override
    public Optional<Grade> findById(Long gradeId) {
        return gradeRepository.findById(gradeId);
    }

    @Transactional
    @Override
    public Grade update(Grade object) {
        return null;
    }

    @Transactional
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
