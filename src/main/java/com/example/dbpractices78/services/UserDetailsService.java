package com.example.dbpractices78.services;

import com.example.dbpractices78.models.User;
import com.example.dbpractices78.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Vlad Utts
 */
@Service
@Transactional(readOnly = true)
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByUsername(username);
        if (optional.isEmpty())
            throw new UsernameNotFoundException("User not found!");

        return new com.example.dbpractices78.security.UserDetails(optional.get());
    }
}
