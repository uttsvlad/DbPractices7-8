package com.example.dbpractices78.repositories;

import com.example.dbpractices78.models.EmployeePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vlad Utts
 */
@Repository
public interface EmployeePositionRepository extends JpaRepository<EmployeePosition, Long> {
    EmployeePosition findByPositionName(String positionName);
}
