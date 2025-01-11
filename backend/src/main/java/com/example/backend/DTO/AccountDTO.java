package com.example.backend.DTO;

import com.example.backend.entities.Account;
import lombok.Getter;
import lombok.Setter;

/**
 * java class that is used as a singleton data transfer object to ensure only necessary data is being transferred.
 */

@Getter
@Setter

public class AccountDTO {
    //private fields to store the singleton values of the requested account.
    private Long id;
    private String email;
    private String role;
    private String userName;
    private String dateOfBirth;
    private String password;
    private String jwtToken;

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.email = account.getEmail();
        this.role = account.getRole();
        this.userName = account.getUserName();
        this.password = account.getPassword();
//        this.dateOfBirth = account.getDateOfBirth().toString();
    }
//helper  method to verify if password i corresponding to the account.
    public boolean verifyPassword(String rawPassword){
        return org.springframework.security.crypto.bcrypt.BCrypt.checkpw(rawPassword, this.password);}
}
