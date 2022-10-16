package com.example.dbpractices78.repositories;

import com.example.dbpractices78.models.Employee;
import com.example.dbpractices78.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Vlad Utts
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByAuthorOrExecutor(Employee employee, Employee sameEmployee);

    List<Task> findAllByExecutorAndCreationDateBetween(Employee employee, Date start, Date end);
}
