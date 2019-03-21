package com.github.stonedch.socialnetwork.controller;

import com.github.stonedch.socialnetwork.domain.Account;
import com.github.stonedch.socialnetwork.domain.Story;
import com.github.stonedch.socialnetwork.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private StoryRepository storyRepository;
    @Value("${upload.path}")
    private String uploadPath;

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
                           @AuthenticationPrincipal Account account,
                           @RequestParam("file") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuid = UUID.randomUUID().toString();
            String resultFilename = uuid + "-" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            System.out.println("### TEST | Path: " + uploadDir.getAbsolutePath() + "| Filename: " + resultFilename);
            story.setFilename(resultFilename);
        }

        story.setAuthor(account);
        storyRepository.save(story);
        return "redirect:/home";
    }
}
