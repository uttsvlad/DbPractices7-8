package com.example.dbpractices78.controllers;

import com.example.dbpractices78.dto.EmployeeDTO;
import com.example.dbpractices78.dto.EmployeeUserDTO;
import com.example.dbpractices78.dto.UserDTO;
import com.example.dbpractices78.models.Employee;
import com.example.dbpractices78.models.User;
import com.example.dbpractices78.services.EmployeeService;
import com.example.dbpractices78.services.UserService;
import com.example.dbpractices78.util.validation.employee.EmployeeValidator;
import com.example.dbpractices78.util.validation.user.UserValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author Vlad Utts
 */
@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeValidator employeeValidator;
    private final UserService userService;
    private final UserValidator userValidator;
    @Value("${user.role.manager}")
    private String ROLE_MANAGER;
    @Value("${user.role.clerk}")
    private String ROLE_CLERK;

    public EmployeeController(EmployeeService employeeService, EmployeeValidator employeeValidator, UserService userService, UserValidator userValidator) {
        this.employeeService = employeeService;
        this.employeeValidator = employeeValidator;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/new")
    public String newEmployee(@ModelAttribute("employeeUserDTO") EmployeeUserDTO employeeUserDTO) {
        return "employees/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("employeeUserDTO") @Valid EmployeeUserDTO employeeUserDTO,
                         BindingResult bindingResult) {
        EmployeeDTO employeeDTO = employeeService.formEmployeeDTO(employeeUserDTO);
        UserDTO userDTO = userService.formUserDTO(employeeUserDTO);

        employeeValidator.validate(employeeDTO, bindingResult);
        userValidator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors())
            return "/employees/new";

        Employee employee = employeeService.save(employeeDTO);
        User user = userService.save(userDTO, employee);
        if (user.getRole().equals(ROLE_MANAGER))
            return "redirect:/manager/info";
        else
            return "redirect:/clerk/info/";
    }
}
