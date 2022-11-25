package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.exep.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }

    @Override
    @Transactional
    public void update(int id, User updateUser) {
        User user = userRepository.getById(id);

        if (updateUser.getPassword().equals(user.getPassword())) {
            userRepository.update(updateUser);
        } else {
            String pass = passwordEncoder.encode(updateUser.getPassword());
            updateUser.setPassword(pass);
            userRepository.update(updateUser);
        }
    }

    @Override
    public User getById(int id) {
        Optional<User> foundUser = Optional.ofNullable(userRepository.getById(id));
        return foundUser.orElseThrow(UserNotFoundException::new);    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            if (userRepository.findUserByUsername(user.getUsername()) != null) {
                throw new NonUniqueResultException("Ошибка: логин '" + user.getUsername() + "' уже занят.");
            }
        } catch (EmptyResultDataAccessException | NoResultException ignored) {
        }
        userRepository.save(user);    }
    @Transactional
    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
