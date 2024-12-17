package com.example.backend.repositories;

import com.example.backend.entities.Account;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
//    @Query("SELECT a FROM Account a WHERE a.email = :email")
    List<Account>findByEmail(String email);
    List<Account> findAccountById(Long id);
    List<Account>deleteAccountById(Long id);
}
