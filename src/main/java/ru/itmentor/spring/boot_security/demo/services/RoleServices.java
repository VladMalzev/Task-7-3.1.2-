package ru.itmentor.spring.boot_security.demo.services;

import ru.itmentor.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleServices {
    Role findByName(String name);
    List<Role> findAll();
}
