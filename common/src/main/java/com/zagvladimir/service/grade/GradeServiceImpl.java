package com.zagvladimir.service.grade;

import com.zagvladimir.domain.Grade;
import com.zagvladimir.domain.Item;
import com.zagvladimir.domain.enums.Status;
import com.zagvladimir.domain.user.User;
import com.zagvladimir.dto.requests.grade.GradeCreateRequest;
import com.zagvladimir.dto.response.GradeResponse;
import com.zagvladimir.mappers.GradeMapper;
import com.zagvladimir.repository.GradeRepository;
import com.zagvladimir.repository.ItemRepository;
import com.zagvladimir.repository.UserRepository;
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
  private final ItemRepository itemRepository;
  private final UserRepository userRepository;
  private final GradeMapper gradeMapper;

  @Override
  public Page<GradeResponse> findAll(Pageable page) {
    return gradeRepository.findAll(page).map(gradeMapper::toResponse);
  }

  @Transactional
  @Override
  public GradeResponse create(GradeCreateRequest gradeCreateRequest) {
    Long userId = gradeCreateRequest.getUserId();
    Long itemId = gradeCreateRequest.getItemId();

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " not found"));

    Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new EntityNotFoundException("Item with id: " + itemId + " not found"));

    Grade newGrade = gradeMapper.convertCreateRequest(gradeCreateRequest);
    newGrade.setUser(user);
    newGrade.setItem(item);

    return gradeMapper.toResponse(gradeRepository.save(newGrade));
  }

  @Override
  public GradeResponse findById(Long gradeId) {
    Grade grade = getGradeById(gradeId);
    return gradeMapper.toResponse(grade);
  }

  @Transactional
  @Override
  public void delete(Long gradeId) {
    Grade grade = getGradeById(gradeId);
    gradeRepository.delete(grade);
  }

  @Transactional
  @Override
  public void softDelete(Long gradeId) {
    Grade grade = getGradeById(gradeId);
    grade.setStatus(Status.DELETED);
    gradeRepository.save(grade);
  }

  @Override
  public String getLoginWhoRatedByGradeId(Long id) {
    String login = gradeRepository.getLoginWhoRatedByGradeId(id);
    if (login == null) {
      throw new EntityNotFoundException("User not found");
    }
    return login;
  }

  private Grade getGradeById(Long gradeId) {
    return gradeRepository.findById(gradeId)
            .orElseThrow(() -> new EntityNotFoundException(
                    String.format("The grade with id:%d not found", gradeId)));
  }
}
