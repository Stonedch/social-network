package com.github.stonedch.socialnetwork.service;

import com.github.stonedch.socialnetwork.domain.Account;
import com.github.stonedch.socialnetwork.domain.Role;
import com.github.stonedch.socialnetwork.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MailSender mailSender;
    private String activationLink = "http://localhost:8080/registration/activate";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username);
    }

    public boolean addAccount(Account account) {
        Account accountFromDatabase = accountRepository.findByUsername(account.getUsername());

        if (accountFromDatabase != null) {
            return false;
        }

        account.setActive(true);
        account.setRoles(Collections.singleton(Role.USER));
        account.setActivationCode(UUID.randomUUID().toString());

        accountRepository.save(account);

        if (!account.getEmail().isEmpty()) {
            String message = String.format(
                    "Hello, %s!\n Please, visit this link: %s/%s",
                    account.getUsername(),
                    activationLink.toString(),
                    account.getActivationCode()
            );
            mailSender.send(account.getEmail(), "Activation code", message);
        }

        return true;
    }

    public boolean activateAccount(String activationCode) {
        Account account = accountRepository.findByActivationCode(activationCode);

        if (account == null) {
            return false;
        }

        account.setActivationCode(null);
        accountRepository.save(account);

        return true;
    }
}
