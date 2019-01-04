package com.github.stonedch.socialnetwork.controller;

import com.github.stonedch.socialnetwork.domain.Account;
import com.github.stonedch.socialnetwork.domain.Story;
import com.github.stonedch.socialnetwork.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private StoryRepository storyRepository;

    @GetMapping
    public String getHome(@RequestParam(required = false) String filter,
                          Model model) {
        if (filter != null && !filter.isEmpty()) {
            model.addAttribute("filter", filter);
            model.addAttribute("storyList", storyRepository.findByTag(filter));
        } else {
            model.addAttribute("storyList", storyRepository.findAll());
        }
        model.addAttribute("newStory", new Story());
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
