package com.example.dbpractices78.util.validation.user;

import com.example.dbpractices78.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * @author Vlad Utts
 */
@Component
public class UserValidator implements Validator {
    private final UserDetailsService userDetailsService;

    public UserValidator(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        try {
            userDetailsService.loadUserByUsername(userDTO.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return; // все ок, пользователь не найден
        }

        errors.rejectValue("username", "", "Пользователь с таким именем уже существует!");
    }
}
