package com.example.backend.repositories;

import com.example.backend.entities.Constituency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstituencyRepository extends JpaRepository<Constituency, Long> {

}
