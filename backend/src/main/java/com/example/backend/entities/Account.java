package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Represents an account entity.
 * This entity is used to store account information.
 */
@Entity
@Getter
@Setter
public class Account {
    /**
     * Unique identifier for the account, automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    /**
     * Fields that hold the actual account's information.
     */
    private Long id;
    private String email;
    private String password;
    private String role;
    private String userName;
    /**
     * The date of birth of the account , stored as a @temporal temporaltype.DATE
     * json representation of this field is formatted as "yyyy-mm-dd"
     * with the time zone set to europe amsterdam for consistency purposes.
     */
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Amsterdam")
    private Date dateOfBirth;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", userName='" + userName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    public String getDateOfBirthString() {
        return this.getDateOfBirthString();
    }
}
