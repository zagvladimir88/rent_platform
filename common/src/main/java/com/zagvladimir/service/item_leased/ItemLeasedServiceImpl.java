package com.zagvladimir.service.item_leased;

import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.domain.enums.Status;
import com.zagvladimir.domain.user.User;
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

@Service
@RequiredArgsConstructor
public class ItemLeasedServiceImpl implements ItemLeasedService {

  private final ItemLeasedRepository itemLeasedRepository;
  private final UserRepository userRepository;


  @Override
  public Page<ItemLeased> findAll(Pageable page) {
    return itemLeasedRepository.findAll(page);
  }

  @Override
  @Transactional
  public ItemLeased create(ItemLeased itemLeased, Long renterId) {
    Optional<User> optionalUser = userRepository.findById(renterId);
    if(optionalUser.isPresent()) {
      itemLeased.setRenter(optionalUser.get());
      itemLeased.setStatus(Status.NOT_ACTIVE);
    } else this new EntityNotFoundException("User with id: " + renterId + "not found");
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

  @Override
  public String getRenterName(Long id) {
    return itemLeasedRepository.getRenterName(id);
  }

  @Override
  public List<ItemLeased> findAllByRenterId(Long useId) {
    return itemLeasedRepository.findAllByRenterId(useId);
  }
}
