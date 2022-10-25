package com.zagvladimir.service.item_leased;

import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.repository.ItemLeasedRepository;
import com.zagvladimir.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemLeasedServiceImpl implements ItemLeasedService {

    private final ItemLeasedRepository itemLeasedRepository;
    private final UserService userService;

    @Override
    public List<ItemLeased> findAll() {
        return itemLeasedRepository.findAll();
    }

    @Transactional
    @Override
    public ItemLeased create(ItemLeased itemLeased, Long renterId) {
        itemLeased.setRenter(userService.findById(renterId));
        return itemLeasedRepository.save(itemLeased);
    }

    @Override
    public ItemLeased findById(Long itemLeasedId) {
        return itemLeasedRepository.findById(itemLeasedId).orElseThrow(EntityNotFoundException::new);
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
