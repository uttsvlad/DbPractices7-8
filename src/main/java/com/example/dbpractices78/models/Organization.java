package com.example.dbpractices78.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Vlad Utts
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Organization {
    @Id
    @Column(name = "organization_itn")
    private String organizationITN;
    private String organizationName;
    private String phoneNumber;
    private String address;
    private String email;
    private Boolean activeStatus;
}
