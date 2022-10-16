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
@Table(name = "_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String username;
    private String password;
    private String role;
    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;
}
