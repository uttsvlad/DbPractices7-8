package com.example.dbpractices78.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Vlad Utts
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private String fullName;
    private String passportSerialNumber;
    private String email;
    private String phoneNumber;
    private String positionName;
}
