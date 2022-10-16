package com.example.dbpractices78.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Vlad Utts
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee_position")
public class EmployeePosition {
    @Id
    @Column(name = "position_id")
    private Long positionId;
    private String positionName;
}
