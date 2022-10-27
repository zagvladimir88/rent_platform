package com.zagvladimir.service.item_leased;

import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.repository.ItemLeasedRepository;
import com.zagvladimir.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;



@Service
@RequiredArgsConstructor
public class ItemLeasedServiceImpl implements ItemLeasedService {

  private final ItemLeasedRepository itemLeasedRepository;
  private final UserService userService;

  @Override
  public Page<ItemLeased> findAll(Pageable page) {
    return itemLeasedRepository.findAll(page);
  }

  @Transactional
  @Override
  public ItemLeased create(ItemLeased itemLeased, Long renterId) {
    itemLeased.setRenter(userService.findById(renterId));
    return itemLeasedRepository.save(itemLeased);
  }

  @Override
  public ItemLeased findById(Long itemLeasedId) {
    return itemLeasedRepository
        .findById(itemLeasedId)
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
}
