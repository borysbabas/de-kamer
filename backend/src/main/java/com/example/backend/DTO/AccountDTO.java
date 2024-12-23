package com.example.backend.DTO;

import com.example.backend.entities.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
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

    public boolean verifyPassword(String rawPassword){
        return org.springframework.security.crypto.bcrypt.BCrypt.checkpw(rawPassword, this.password);}
}
