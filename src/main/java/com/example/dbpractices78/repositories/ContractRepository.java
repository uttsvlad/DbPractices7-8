package com.example.dbpractices78.repositories;

import com.example.dbpractices78.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vlad Utts
 */
@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
}
