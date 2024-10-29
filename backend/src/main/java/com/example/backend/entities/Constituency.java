package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "constituencies")
public class Constituency {
    public Constituency() {}

    public Constituency(Long constituencyId, String constituencyName) {
        this.constituencyId = constituencyId;
        this.constituencyName = constituencyName;
    }

    @Id
    @Column(name = "constituency_id")
    private Long constituencyId;

    @Column(name = "constituency_name")
    private String constituencyName;

    @OneToMany(mappedBy = "constituency")
    private List<Municipality> municipalities;
}