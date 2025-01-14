package com.example.backend.repositories;

import com.example.backend.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing database operations on the required Account entity.
 *
 * Extends JpaRepository to provide standard CRUD operations e.g.(findByEmail, findByAccountId and deleteAccountById).
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account>findByEmail(String email);
    List<Account> findAccountById(Long id);
    void deleteAccountById(Long id);
}
