package com.zagvladimir.repository.ItemLeased;

import com.zagvladimir.domain.ItemLeased;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class ItemLeasedRepository implements ItemLeasedRepositoryInterface {

  private final SessionFactory sessionFactory;

  @Override
  public ItemLeased findById(Long id) {
    try (Session session = sessionFactory.openSession()) {
      return session.get(ItemLeased.class, id);
    }
  }

  @Override
  public Optional<ItemLeased> findOne(Long id) {
    return Optional.of(findById(id));
  }

  @Override
  public List<ItemLeased> findAll() {
    return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
  }

  @Override
  public List<ItemLeased> findAll(int limit, int offset) {
    try (Session session = sessionFactory.openSession()) {
      return session
          .createQuery("select il from ItemLeased il", ItemLeased.class)
          .setFirstResult(offset)
          .setMaxResults(limit)
          .getResultList();
    }
  }

  @Override
  public ItemLeased create(ItemLeased object) {
    try (Session session = sessionFactory.openSession()) {
      session.saveOrUpdate(object);
      return findById(object.getId());
    }
  }

  @Override
  public ItemLeased update(ItemLeased object) {
    try (Session session = sessionFactory.openSession()) {
      session.saveOrUpdate(object);
      return findById(object.getId());
    }
  }

  @Override
  public Long delete(Long id) {
    try (Session session = sessionFactory.openSession()) {
      ItemLeased deleteItemLeased = session.get(ItemLeased.class, id);
      session.delete(deleteItemLeased);
      return id;
    }
  }
}
