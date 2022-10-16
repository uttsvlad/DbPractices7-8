package com.example.dbpractices78.repositories;

import com.example.dbpractices78.models.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vlad Utts
 */
@Repository
public interface StatusRepository extends JpaRepository<TaskStatus, Long> {
    TaskStatus findByStatusName(String statusName);
}
