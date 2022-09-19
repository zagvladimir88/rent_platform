package com.zagvladimir.repository.user;

import com.zagvladimir.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.*;

@Log4j2
@Repository
@RequiredArgsConstructor
@Primary
public class UserRepository implements UserRepositoryInterface {

  private final SessionFactory sessionFactory;

  @Override
  public User findById(Long id) {
    try (Session session = sessionFactory.openSession()) {
      return session.get(User.class, id);
    }
  }

  @Override
  public Optional<User> findOne(Long id) {
    return Optional.of(findById(id));
  }

  @Override
  public List<User> findAll() {
    return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
  }

  @Override
  public List<User> findAll(int limit, int offset) {
    try (Session session = sessionFactory.openSession()) {

      return session
          .createQuery("select u from User u", User.class)
          .setFirstResult(offset)
          .setMaxResults(limit)
          .getResultList();
    }
  }

  @Override
  public User create(User object) {
    try (Session session = sessionFactory.openSession()) {
      session.saveOrUpdate(object);
      return findById(object.getId());
    }
  }

  @Override
  public User update(User object) {
    try (Session session = sessionFactory.openSession()) {
      session.saveOrUpdate(object);
      return findById(object.getId());
    }
  }

  @Override
  public Long delete(Long id) {
    try (Session session = sessionFactory.openSession()) {
      User deleteUser = session.get(User.class, id);
      session.delete(deleteUser);
      return id;
    }
  }

  @Override
  public Map<String, Object> getUserStats() {
    return new HashMap<>();
  }

  @Override
  public Optional<User> findByLogin(String login) {
    try (Session session = sessionFactory.openSession()) {
      return Optional.of(session.createQuery("select u from User u where u.userLogin = '" + login +"'", User.class).getSingleResult());
    }
  }
}
