package com.zagvladimir.repository.ItemLeased;

import com.zagvladimir.domain.Item;
import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.domain.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class ItemLeasedRepository implements ItemLeasedRepositoryInterface{

    private final SessionFactory sessionFactory;

    @Override
    public ItemLeased findById(Long id) {
        Session session = sessionFactory.openSession();
        return session.get(ItemLeased.class,id);
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
        Session session = sessionFactory.openSession();
        return session.createQuery("select il from ItemLeased il", ItemLeased.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public ItemLeased create(ItemLeased object) {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(object);
        return findById(object.getId());
    }

    @Override
    public ItemLeased update(ItemLeased object) {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(object);
        return findById(object.getId());
    }

    @Override
    public Long delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        ItemLeased deleteItemLeased = session.get(ItemLeased.class, id);
        session.delete(deleteItemLeased);
        return id;
    }
}
