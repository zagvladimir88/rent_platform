package com.zagvladimir.service.item_leased;

import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.domain.enums.Status;
import com.zagvladimir.repository.ItemLeasedRepository;
import com.zagvladimir.service.mail.MailSenderService;
import com.zagvladimir.service.item.ItemService;
import com.zagvladimir.service.pdf.PDFService;
import com.zagvladimir.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemLeasedServiceImpl implements ItemLeasedService {

  private final ItemLeasedRepository itemLeasedRepository;
  private final ItemService itemService;
  private final UserService userService;
  private final MailSenderService mailSenderService;
  private final PDFService pdfService;

  @Override
  public Page<ItemLeased> findAll(Pageable page) {
    return itemLeasedRepository.findAll(page);
  }

  @Override
  @Transactional
  public ItemLeased create(ItemLeased itemLeased, Long renterId) {
    itemLeased.setRenter(userService.findById(renterId));
    itemLeased.setStatus(Status.NOT_ACTIVE);
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
