package com.zagvladimir.service.grade;

import com.zagvladimir.domain.Grade;
import com.zagvladimir.domain.enums.Status;
import com.zagvladimir.domain.user.User;
import com.zagvladimir.dto.requests.grade.GradeCreateRequest;
import com.zagvladimir.dto.response.GradeResponse;
import com.zagvladimir.mappers.GradeMapper;
import com.zagvladimir.repository.GradeRepository;
import com.zagvladimir.repository.ItemRepository;
import com.zagvladimir.repository.UserRepository;
import com.zagvladimir.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

  private final GradeRepository gradeRepository;
  private final ItemService itemService;
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
    Grade newGrade = gradeMapper.convertCreateRequest(gradeCreateRequest);
    Optional<User> optionalUser = userRepository.findById(userId);
    if(optionalUser.isPresent()) {
      newGrade.setUser(optionalUser.get());
      newGrade.setItem(itemRepository.findById(itemId).get());
    }else throw new EntityNotFoundException("User with id: " + userId + " not found");

        return gradeMapper.toResponse(gradeRepository.save(newGrade));
  }

  @Override
  public GradeResponse findById(Long gradeId) {

    return gradeRepository
        .findById(gradeId)
            .map(gradeMapper::toResponse)
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

  @Override
  public String getLoginWhoRatedByGradeId(Long id) {
    String login = gradeRepository.getLoginWhoRatedByGradeId(id);
    if(login == null) {
      throw new EntityNotFoundException("user not found");
    }
    return login;
  }
}
