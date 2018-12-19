package com.github.stonedch.socialnetwork.controller;

import com.github.stonedch.socialnetwork.domain.Account;
import com.github.stonedch.socialnetwork.domain.Role;
import com.github.stonedch.socialnetwork.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addAccount(Account account) {
        Account accountFromBase = accountRepository.findByUsername(account.getUsername());
        account.setActive(true);
        account.setRoles(Collections.singleton(Role.USER));
        accountRepository.save(account);
        return "redirect:/login";
    }
}
