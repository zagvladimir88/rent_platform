package com.zagvladimir.repository.role;

import com.zagvladimir.domain.Role;
import com.zagvladimir.domain.SystemRoles;
import com.zagvladimir.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
@RequiredArgsConstructor
@Primary
public class RoleRepository implements RoleRepositoryInterface{

    private final SessionFactory sessionFactory;

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class,userId);
        List<Role> allRoles = user.getRoles();
        return user.getRoles();
    }



    @Override
    public Role findById(Long id) {
        Session session = sessionFactory.openSession();

        return session.get(Role.class,id);
    }

    @Override
    public Optional<Role> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<Role> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT,DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<Role> findAll(int limit, int offset) {
        Session session = sessionFactory.openSession();

        return session.createQuery("select r from Role r", Role.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Role create(Role object) {
        Session session = sessionFactory.openSession();

        session.saveOrUpdate(object);

        return findById(object.getId());
    }

    @Override
    public Role update(Role object) {
        Session session = sessionFactory.openSession();

        session.saveOrUpdate(object);

        return findById(object.getId());
    }

    @Override
    public Long delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Role deleteRole = session.get(Role.class,id);
        session.delete(deleteRole);
        return id;
    }
}
