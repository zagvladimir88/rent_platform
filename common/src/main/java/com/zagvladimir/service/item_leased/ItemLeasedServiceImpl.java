package com.zagvladimir.service.item_leased;

import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.domain.enums.Status;
import com.zagvladimir.domain.user.User;
import com.zagvladimir.dto.requests.items_leased.ItemLeasedCreateRequest;
import com.zagvladimir.dto.response.ItemLeasedResponse;
import com.zagvladimir.mappers.ItemLeasedMapper;
import com.zagvladimir.repository.ItemLeasedRepository;
import com.zagvladimir.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemLeasedServiceImpl implements ItemLeasedService {

  private final ItemLeasedRepository itemLeasedRepository;
  private final UserRepository userRepository;
  private final ItemLeasedMapper itemLeasedMapper;


  @Override
  public Page<ItemLeasedResponse> findAll(Pageable page) {
    return itemLeasedRepository.findAll(page).map(itemLeasedMapper::toResponse);
  }

  @Override
  @Transactional
  public ItemLeasedResponse create(ItemLeasedCreateRequest request) {
    ItemLeased itemLeased = itemLeasedMapper.convertCreateRequest(request);
    Long renterId = request.getRenterId();
    Optional<User> optionalUser = userRepository.findById(renterId);
    if(optionalUser.isPresent()) {
      itemLeased.setRenter(optionalUser.get());
      itemLeased.setStatus(Status.NOT_ACTIVE);
    } else throw  new EntityNotFoundException("User with id: " + renterId + "not found");
    return itemLeasedMapper.toResponse(itemLeasedRepository.save(itemLeased));
  }

  @Override
  public ItemLeasedResponse findById(Long itemLeasedId) {
    return itemLeasedRepository
        .findById(itemLeasedId)
            .map(itemLeasedMapper::toResponse)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format("The item leased with id:%d not found", itemLeasedId)));
  }

  @Transactional
  @Override
  public ItemLeased update(ItemLeased object) {
    return itemLeasedRepository.save(object);
  }

  @Transactional
  @Override
  public Long delete(Long itemLeasedId) {
    itemLeasedRepository.deleteById(itemLeasedId);
    return itemLeasedId;
  }

  @Override
  public String getRenterName(Long id) {
    return itemLeasedRepository.getRenterName(id);
  }

  @Override
  public List<ItemLeasedResponse> findAllByRenterId(Long userId) {
    return itemLeasedRepository.findAllByRenterId(userId).stream()
            .map(itemLeasedMapper::toResponse)
            .collect(Collectors.toList());
  }
}
