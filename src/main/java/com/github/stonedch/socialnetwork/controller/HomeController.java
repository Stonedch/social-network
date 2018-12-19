package com.github.stonedch.socialnetwork.controller;

import com.github.stonedch.socialnetwork.domain.Account;
import com.github.stonedch.socialnetwork.domain.Story;
import com.github.stonedch.socialnetwork.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private StoryRepository storyRepository;

    @GetMapping
    public String getHome(Model model) {
        model.addAttribute("newStory", new Story());
        model.addAttribute("storyList", storyRepository.findAll());
        return "home";
    }

    @PostMapping
    public String addStory(@ModelAttribute Story story,
                           @AuthenticationPrincipal Account account) {
        story.setAuthor(account);
        storyRepository.save(story);
        return "redirect:/home";
    }
}
