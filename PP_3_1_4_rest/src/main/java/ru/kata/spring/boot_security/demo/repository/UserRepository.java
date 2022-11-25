package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    User findUserByUsername(String username);
    void save(User user);
    void deleteById(int id);
    void update(User user);
    User getById(int id);
    List<User> findAll();

}
