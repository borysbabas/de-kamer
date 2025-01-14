package com.example.backend.DTO;

import com.example.backend.entities.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminViewAccountDTO {
    private Long id;
    private String email;
    private String role;
    private String userName;
    private String dateOfBirth;
    private String jwtToken;
    private boolean banned;

    public AdminViewAccountDTO(Account account) {
        this.id = account.getId();
        this.email = account.getEmail();
        this.role = account.getRole();
        this.userName = account.getUserName();
        this.banned = account.isBanned();
        try {
            this.dateOfBirth = account.getDateOfBirth().toString();
        } catch (NullPointerException e) {
            this.dateOfBirth = null;
        }
    }
}
