package com.example.dbpractices78.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Vlad Utts
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Watch {
    @Id
    private String serialNumber;
    private String mechanismType;
    private String glassType;
    private String description;
    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "contract_id")
    private Contract contract;
}
