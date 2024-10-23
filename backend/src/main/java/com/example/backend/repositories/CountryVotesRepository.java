package com.example.backend.repositories;

import com.example.backend.entities.CountryVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryVotesRepository extends JpaRepository<CountryVotes, Long> {

}
