package com.feyproj.controller;

import com.feyproj.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    private final PostService postService;

    public ProfileController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/profile/edit")
    public String editProfileForm(Model model, Authentication auth) {
        String username = auth.getName();
        String[] p = postService.profileOf(username);
        model.addAttribute("profile", postService.getProfile(username));
        model.addAttribute("currentUser", username);
        model.addAttribute("displayName", p[0]);
        model.addAttribute("avatarInitial", p[1]);
        model.addAttribute("avatarColor", p[2]);
        return "profile-edit";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@RequestParam String displayName,
                                @RequestParam(required = false, defaultValue = "") String bio,
                                @RequestParam String avatarColor,
                                Authentication auth) {
        postService.updateProfile(auth.getName(), displayName, bio, avatarColor);
        return "redirect:/profile/" + auth.getName();
    }

    @GetMapping("/profile/{username}")
    public String viewProfile(@PathVariable String username,
                              Model model, Authentication auth) {
        String currentUser = auth.getName();
        String[] p = postService.profileOf(currentUser);
        model.addAttribute("profile", postService.getProfile(username));
        model.addAttribute("userPosts", postService.getPostsByUser(username));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isOwnProfile", currentUser.equals(username));
        model.addAttribute("displayName", p[0]);
        model.addAttribute("avatarInitial", p[1]);
        model.addAttribute("avatarColor", p[2]);
        return "profile";
    }
}
