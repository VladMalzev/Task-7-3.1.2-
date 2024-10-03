package ru.itmentor.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.repositories.UserRepositories;


import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServicesImpl implements UserServices {

    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServicesImpl(UserRepositories userRepositories, PasswordEncoder passwordEncoder){
        this.userRepositories=userRepositories;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> index() {
        return userRepositories.findAll();
    }

    @Override
    public User show(Long id) {
        return userRepositories.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepositories.findByUsername(username);
    }

    @Transactional
    @Override
    public void save(User user) {
        userRepositories.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepositories.deleteById(id);
    }

    @Transactional
    @Override
    public void update(User user, Long id) {
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepositories.save(user);
    }
    @Transactional
    @Override
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepositories.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepositories.findByUsernameAndFetchLazyRelationEagerly(username);
    }
}
