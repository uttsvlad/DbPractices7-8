package com.example.dbpractices78.util.validation.employee;

import com.example.dbpractices78.dto.EmployeeDTO;
import com.example.dbpractices78.models.Employee;
import com.example.dbpractices78.repositories.EmployeeRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

/**
 * @author Vlad Utts
 */
@Component
public class EmployeeValidator implements Validator {
    private final EmployeeRepository employeeRepository;

    public EmployeeValidator(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return EmployeeDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeDTO employeeDTO = (EmployeeDTO) target;

        String passportSerialNumber = employeeDTO.getPassportSerialNumber();
        String email = employeeDTO.getEmail();
        String phoneNumber = employeeDTO.getPhoneNumber();

        Optional<Employee> byPassportSerialNumber = employeeRepository.findByPassportSerialNumber(passportSerialNumber);
        Optional<Employee> byEmail = employeeRepository.findByEmail(email);
        Optional<Employee> byPhoneNumber = employeeRepository.findByPhoneNumber(phoneNumber);
        if (byPassportSerialNumber.isPresent())
            errors.rejectValue("passportSerialNumber", "", "Работник с такими паспорными данными уже существует!");
        if (byEmail.isPresent())
            errors.rejectValue("email", "", "Работник с таким email уже существует!");
        if (byPhoneNumber.isPresent())
            errors.rejectValue("phoneNumber", "", "Работник с таким номером телефона уже существует!");
    }
}
