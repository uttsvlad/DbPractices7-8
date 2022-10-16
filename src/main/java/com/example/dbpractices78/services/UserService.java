package com.example.dbpractices78.services;

import com.example.dbpractices78.dto.EmployeeUserDTO;
import com.example.dbpractices78.dto.UserDTO;
import com.example.dbpractices78.models.Employee;
import com.example.dbpractices78.models.EmployeePosition;
import com.example.dbpractices78.models.User;
import com.example.dbpractices78.repositories.EmployeeRepository;
import com.example.dbpractices78.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * @author Vlad Utts
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Value("${user.role.manager}")
    private String ROLE_MANAGER;
    @Value("${user.role.clerk}")
    private String ROLE_CLERK;

    @Autowired
    public UserService(UserRepository userRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(UserDTO userDTO, Employee employee) {
        User user = convertToUser(userDTO, employee);
        userRepository.save(user);

        return user;
    }

    public Employee getEmployeeByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            Optional<Employee> optionalEmployee = employeeRepository.findById(optionalUser.get().getEmployee().getEmployeeId());
            if (optionalEmployee.isPresent())
                return optionalEmployee.get();
        }
        throw new UsernameNotFoundException("User with this username not found!");
    }

    private User convertToUser(UserDTO userDTO, Employee employee) {
        User user = modelMapper.map(userDTO, User.class);
        setEncodedPasswordAndEmployee(user, employee);
        EmployeePosition position = employee.getPosition();
        String role;
        if (position.getPositionName().toLowerCase().contains("manager"))
            role = ROLE_MANAGER;
        else
            role = ROLE_CLERK;
        user.setRole(role);

        return user;
    }

    public UserDTO formUserDTO(EmployeeUserDTO employeeUserDTO) {
        return new UserDTO(employeeUserDTO.getUsername(),
                employeeUserDTO.getPassword());
    }

    private void setEncodedPasswordAndEmployee(User user, Employee employee) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmployee(employee);
    }
}
