package ru.kata.spring.boot_security.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public User findUserByUsername(String username) {
        return entityManager.createQuery(
                        "SELECT user FROM User user join fetch user.roles WHERE user.username =:username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
    @Override
    public void save(User user) {
        entityManager.persist(user);
    }
    @Override
    public void deleteById(int id) {
        entityManager.remove(entityManager.find(User.class, id));
    }
    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}

