package com.example.dbpractices78.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Vlad Utts
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String description;
    private Date creationDate;
    private Date deadlineDate;
    private Date completeDate;
    @ManyToOne
    @JoinColumn(name = "priority_code", referencedColumnName = "priority_code")
    private TaskPriority priority;
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    private TaskStatus status;
    @ManyToOne
    @JoinColumn(name = "contact_person_id", referencedColumnName = "contact_person_id")
    private ContactPerson contactPerson;
    @ManyToOne
    @JoinColumn(name = "executor_id", referencedColumnName = "employee_id")
    private Employee executor;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "employee_id")
    private Employee author;
    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "contract_id")
    private Contract contract;
}
