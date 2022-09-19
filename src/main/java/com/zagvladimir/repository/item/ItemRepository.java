package com.zagvladimir.repository.item;

import com.zagvladimir.domain.Item;
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
public class ItemRepository implements ItemRepositoryInterface {

  private final SessionFactory sessionFactory;

  @Override
  public Item findById(Long id) {
    try (Session session = sessionFactory.openSession()) {
      return session.get(Item.class, id);
    }
  }

  @Override
  public Optional<Item> findOne(Long id) {
    return Optional.of(findById(id));
  }

  @Override
  public List<Item> findAll() {
    return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
  }

  @Override
  public List<Item> findAll(int limit, int offset) {
    try (Session session = sessionFactory.openSession()) {
      return session
          .createQuery("select i from Item i", Item.class)
          .setFirstResult(offset)
          .setMaxResults(limit)
          .getResultList();
    }
  }

  @Override
  public Item create(Item object) {
    try (Session session = sessionFactory.openSession()) {
      session.saveOrUpdate(object);
      return findById(object.getId());
    }
  }

  @Override
  public Item update(Item object) {
    try (Session session = sessionFactory.openSession()) {
      session.saveOrUpdate(object);
      return findById(object.getId());
    }
  }

  @Override
  public Long delete(Long id) {
    try (Session session = sessionFactory.openSession()) {
      Item deleteItem = session.get(Item.class, id);
      session.delete(deleteItem);
      return id;
    }
  }

  @Override
  public List<Item> getItemsByCategory(int itemTypeId) {

    try (Session session = sessionFactory.openSession()) {
      return session
          .createQuery("select i from Item i where i.itemTypeId =" + itemTypeId, Item.class)
          .getResultList();
    }
  }

  @Override
  public List<Item> searchItemsByName(String name) {
    try (Session session = sessionFactory.openSession()) {
      return session
          .createQuery("select i from Item i where i.itemName = " + name, Item.class)
          .getResultList();
    }
  }
}
