package ru.itmentor.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.models.User;

import java.util.Collections;

@Service
@Transactional(readOnly = true)
public class RegistrationServiceImpl implements RegistrationService {

    private final UserServices userServices;
    private final RoleServices roleServices;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServiceImpl(UserServices userServices, RoleServices roleServices, PasswordEncoder passwordEncoder) {
        this.userServices = userServices;
        this.roleServices = roleServices;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServices.save(user);
    }
}
