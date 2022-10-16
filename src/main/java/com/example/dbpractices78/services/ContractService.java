package com.example.dbpractices78.services;

import com.example.dbpractices78.models.Contract;
import com.example.dbpractices78.repositories.ContractRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vlad Utts
 */
@Service
public class ContractService {
    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }
}
