package com.github.stonedch.socialnetwork.controller;

import com.github.stonedch.socialnetwork.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public String getAdmin(Model model) {
        model.addAttribute("accountList", accountRepository.findAll());
        return "admin";
    }
}
