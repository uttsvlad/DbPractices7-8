package com.example.dbpractices78.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author Vlad Utts
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String password;
}
