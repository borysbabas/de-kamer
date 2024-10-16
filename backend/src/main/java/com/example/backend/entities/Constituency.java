package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "constituencies")
public class Constituency {
    @Id
    @Column(name = "constituency_id")
    private int constituencyId;

    @Column(name = "constituency_name")
    private String constituencyName;
}