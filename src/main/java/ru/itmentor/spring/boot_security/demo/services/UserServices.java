package ru.itmentor.spring.boot_security.demo.services;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.itmentor.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserServices extends UserDetailsService {
    List<User> index();

    User show(Long id);

    User findByUsername(String username);

    void save (User user);

    void delete(Long id);

    void update(User user, Long id);

    void register(User User);
}
