package ru.itmentor.spring.boot_security.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.services.RoleServices;
import ru.itmentor.spring.boot_security.demo.services.UserServices;
import ru.itmentor.spring.boot_security.demo.util.UserValidator;


@Controller
public class AuthController {

    private final UserValidator userValidator;
    private final RoleServices roleServices;
    private final UserServices userServices;

    @Autowired
    public AuthController(UserValidator userValidator, RoleServices roleServices, UserServices userServices) {
        this.userValidator = userValidator;
        this.roleServices = roleServices;
        this.userServices = userServices;
    }

    @GetMapping("/login")
    public String index() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleServices.findAll());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userServices.register(user);
        return "redirect:/login";
    }
}
