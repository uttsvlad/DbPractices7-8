package com.example.dbpractices78.services;

import com.example.dbpractices78.dto.EmployeeDTO;
import com.example.dbpractices78.dto.EmployeeUserDTO;
import com.example.dbpractices78.models.Employee;
import com.example.dbpractices78.models.EmployeePosition;
import com.example.dbpractices78.repositories.EmployeePositionRepository;
import com.example.dbpractices78.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vlad Utts
 */
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeePositionRepository employeePositionRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeePositionRepository employeePositionRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.employeePositionRepository = employeePositionRepository;
        this.modelMapper = modelMapper;
    }

    public Employee save(EmployeeDTO employeeDTO) {
        Employee employee = convertToEmployee(employeeDTO);
        employeeRepository.save(employee);

        return employee;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findOne(Long id){
        return employeeRepository.findById(id).get();
    }

    private Employee convertToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        EmployeePosition position = employeePositionRepository.findByPositionName(employeeDTO.getPositionName());
        employee.setPosition(position);

        return employee;
    }

    public EmployeeDTO formEmployeeDTO(EmployeeUserDTO employeeUserDTO) {
        return new EmployeeDTO(employeeUserDTO.getFullName(),
                employeeUserDTO.getPassportSerialNumber(),
                employeeUserDTO.getEmail(),
                employeeUserDTO.getPhoneNumber(),
                employeeUserDTO.getPositionName());
    }
}
