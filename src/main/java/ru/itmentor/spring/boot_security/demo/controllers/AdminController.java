package ru.itmentor.spring.boot_security.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.services.RoleServices;
import ru.itmentor.spring.boot_security.demo.services.UserServices;
import ru.itmentor.spring.boot_security.demo.util.UserValidator;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServices userServices;
    private final UserValidator userValidator;
    private final RoleServices roleServices;

    @Autowired
    public AdminController(UserServices userServices, UserValidator userValidator, RoleServices roleServices) {
        this.userServices = userServices;
        this.userValidator = userValidator;
        this.roleServices = roleServices;
    }


    @GetMapping()
    public String infoAboutUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userServices.index());
        model.addAttribute("user", user);
        return "admin/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userServices.show(id));
        return "admin/show";
    }

    @GetMapping("/add")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleServices.findAll());
        return "admin/add";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/add";
        }
        userServices.register(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userServices.show(id));
        model.addAttribute("roles", roleServices.findAll());
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        userServices.update(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userServices.delete(id);
        return "redirect:/admin";
    }

}
