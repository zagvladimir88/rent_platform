package com.zagvladimir.service.grade;

import com.zagvladimir.domain.Grade;
import com.zagvladimir.domain.enums.Status;
import com.zagvladimir.repository.GradeRepository;
import com.zagvladimir.service.item.ItemService;
import com.zagvladimir.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

  private final GradeRepository gradeRepository;
  private final ItemService itemService;
  private final UserService userService;

  @Override
  public Page<Grade> findAll(Pageable page) {
    return gradeRepository.findAll(page);
  }

  @Transactional
  @Override
  public Grade create(Grade grade, Long userId, Long itemId) {
    grade.setUser(userService.findById(userId));
    grade.setItem(itemService.findById(itemId));
    return gradeRepository.save(grade);
  }

  @Override
  public Grade findById(Long gradeId) {

    return gradeRepository
        .findById(gradeId)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format("The grade with id:%d not found", gradeId)));
  }

  @Transactional
  @Override
  public Long delete(Long gradeId) {
    gradeRepository.deleteById(gradeId);
    return gradeId;
  }

  @Override
  public Long softDelete(Long gradeId) {
    Grade toUpdate =
            gradeRepository
                    .findById(gradeId)
                    .orElseThrow(
                            () ->
                                    new EntityNotFoundException(
                                            String.format("The grade with id: %d not found", gradeId)));
    toUpdate.setStatus(Status.DELETED);
    gradeRepository.save(toUpdate);
    return gradeId;
  }
}
