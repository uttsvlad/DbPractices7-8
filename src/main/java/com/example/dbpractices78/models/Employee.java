package com.example.dbpractices78.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Vlad Utts
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;
    private String fullName;
    private String passportSerialNumber;
    private String email;
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "position_id")
    private EmployeePosition position;
}
