package ru.itmentor.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.services.UserServices;

@Component
public class UserValidator implements Validator {

    private final UserServices userServices;

    @Autowired
    public UserValidator(UserServices userServices) {
        this.userServices = userServices;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userServices.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "", "User exist");
        }
    }
}
