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
@Table(name = "task_priority")
public class TaskPriority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priority_code")
    private Long priorityCode;
    private String priorityName;
}
