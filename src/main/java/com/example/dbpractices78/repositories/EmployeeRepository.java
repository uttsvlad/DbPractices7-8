package com.example.dbpractices78.repositories;

import com.example.dbpractices78.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Vlad Utts
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByPassportSerialNumber(String passportSerialNumber);
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByPhoneNumber(String phoneNumber);
}
