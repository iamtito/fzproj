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
public class FeedController {

    private final PostService postService;

    public FeedController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/feed";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/feed")
    public String feed(Model model, Authentication auth) {
        String username = auth.getName();
        String[] profile = postService.profileOf(username);
        model.addAttribute("posts", postService.getAllPosts());
        model.addAttribute("currentUser", username);
        model.addAttribute("displayName", profile[0]);
        model.addAttribute("avatarInitial", profile[1]);
        model.addAttribute("avatarColor", profile[2]);
        return "feed";
    }

    @PostMapping("/feed/post")
    public String createPost(@RequestParam String content, Authentication auth) {
        if (content != null && !content.isBlank()) {
            postService.createPost(auth.getName(), content.trim());
        }
        return "redirect:/feed";
    }

    @PostMapping("/feed/{postId}/like")
    public String likePost(@PathVariable long postId, Authentication auth,
                           HttpServletRequest request) {
        postService.toggleLike(postId, auth.getName());
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/feed");
    }

    @PostMapping("/feed/{postId}/comment")
    public String addComment(@PathVariable long postId,
                             @RequestParam String content,
                             Authentication auth,
                             HttpServletRequest request) {
        if (content != null && !content.isBlank()) {
            postService.addComment(postId, auth.getName(), content.trim());
        }
        String referer = request.getHeader("Referer");
        String base = referer != null ? referer.replaceAll("#.*$", "") : "/feed";
        return "redirect:" + base + "#post-" + postId;
    }
}
