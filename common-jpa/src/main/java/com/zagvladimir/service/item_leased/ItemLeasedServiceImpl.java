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

  @Transactional
  @Override
  public ItemLeasedResponse create(ItemLeasedCreateRequest request) {
    User renter = userRepository.findById(request.getRenterId())
            .orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %d not found", request.getRenterId())));

    ItemLeased itemLeased = itemLeasedMapper.convertCreateRequest(request);
    itemLeased.setRenter(renter);
    itemLeased.setStatus(Status.NOT_ACTIVE);

    ItemLeased savedItemLeased = itemLeasedRepository.save(itemLeased);

    return itemLeasedMapper.toResponse(savedItemLeased);
  }

  @Override
  public ItemLeasedResponse findById(Long itemLeasedId) {
    ItemLeased itemLeased = itemLeasedRepository.findById(itemLeasedId)
            .orElseThrow(() -> new EntityNotFoundException(String.format("The item leased with id:%d not found", itemLeasedId)));

    return itemLeasedMapper.toResponse(itemLeased);
  }

  @Transactional
  @Override
  public ItemLeased update(ItemLeased itemLeased) {
    return itemLeasedRepository.save(itemLeased);
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
    List<ItemLeased> itemLeasedList = itemLeasedRepository.findAllByRenterId(userId);
    return itemLeasedList.stream().map(itemLeasedMapper::toResponse).collect(Collectors.toList());
  }
}
