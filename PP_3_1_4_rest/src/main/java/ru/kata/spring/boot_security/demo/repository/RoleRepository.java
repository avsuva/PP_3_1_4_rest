package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository{
    List<Role> findAllRoles();
    void save(Role role);
    Role getById(int id);
    Role getRole(String role);
    public Set<Role> getSetOfRoles(String[] roleNames);

}
