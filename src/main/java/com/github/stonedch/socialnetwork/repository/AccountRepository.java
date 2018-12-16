package com.github.stonedch.socialnetwork.repository;

import com.github.stonedch.socialnetwork.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);
}
