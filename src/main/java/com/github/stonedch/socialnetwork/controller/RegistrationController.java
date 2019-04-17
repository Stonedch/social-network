package com.github.stonedch.socialnetwork.controller;

import com.github.stonedch.socialnetwork.domain.Account;
import com.github.stonedch.socialnetwork.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addAccount(Account account, Model model) {
        if (!accountService.addAccount(account)) {
            model.addAttribute("message", "Account exists!");
            return "redirect:/registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{activationCode}")
    public String activate(@PathVariable String activationCode,
                           Model model) {
        boolean isActivated = accountService.activateAccount(activationCode);

        if (isActivated) {
            model.addAttribute("message", "Account successfully activated.");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }

        return "redirect:/login";
    }
}
