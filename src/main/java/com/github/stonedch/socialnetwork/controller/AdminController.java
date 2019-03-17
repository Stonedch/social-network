package com.github.stonedch.socialnetwork.controller;

import com.github.stonedch.socialnetwork.domain.Account;
import com.github.stonedch.socialnetwork.domain.Role;
import com.github.stonedch.socialnetwork.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public String getAdmin(Model model) {
        model.addAttribute("accountList", accountRepository.findAll());
        return "admin";
    }

    @GetMapping("{account}")
    public String getAccountEditForm(@PathVariable Account account,
                                     Model model) {
        model.addAttribute("account", account);
        model.addAttribute("roles", Role.values());
        return "accountEdit";
    }

    @PostMapping()
    public String saveAccount(@RequestParam("id") Account account,
                              @RequestParam("username") String username,
                              @RequestParam("roles") Set<String> rolesFromForm) {
        account.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        account.getRoles().clear();

        for (String role : rolesFromForm) {
            if (roles.contains(role)) {
                account.getRoles().add(Role.valueOf(role));
            }
        }

        accountRepository.save(account);

        return "redirect:/admin";
    }
}
