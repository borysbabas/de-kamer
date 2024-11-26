package com.example.backend.repositories;

import com.example.backend.entities.Municipality;
import com.example.backend.entities.MunicipalityVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MunicipalityVotesRepository extends JpaRepository<MunicipalityVotes, Long> {
    List<MunicipalityVotes> findByMunicipalityIn(Collection<Municipality> municipalities);
}
