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
@Table(name = "contact_person")
public class ContactPerson {
    @Id
    @Column(name = "contact_person_id")
    private Long contactPersonId;
    private String phoneNumber;
    private String fullName;
    private String email;
    @ManyToOne
    @JoinColumn(name = "organization_itn", referencedColumnName = "organization_itn")
    private Organization organization;
}
