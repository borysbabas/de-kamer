package com.example.backend.repositories;

import com.example.backend.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account>findByEmail(String email);
    List<Account> findAccountById(Long id);
    void deleteAccountById(Long id);
}
