package com.feyproj.service;

import com.feyproj.model.Comment;
import com.feyproj.model.Post;
import com.feyproj.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final Map<String, UserProfile> profiles = new ConcurrentHashMap<>();
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();

    public PostService() {
        seedProfiles();
        seedPosts();
    }

    private void seedProfiles() {
        profiles.put("fey",     new UserProfile("fey",     "Fey",     "Hiker, reader, and coffee enthusiast. ☕🏔️", "#6C63FF"));
        profiles.put("jasline", new UserProfile("jasline", "Jasline", "Software engineer by day, gamer by night. 🎮💻", "#FF6584"));
        profiles.put("kaylah",  new UserProfile("kaylah",  "Kaylah",  "Baker, bookworm, and lover of all things cozy. 📚🥧", "#43C6AC"));
    }

    private void seedPosts() {
        addSeedPost("fey",
                "Just finished hiking the Blue Ridge Trail! The views were absolutely breathtaking. 🏔️ Nature therapy at its finest.", 8);
        addSeedPost("jasline",
                "Hot take: tabs are better than spaces. Fight me. 😤 #DevLife #CodingDebates", 6);
        addSeedPost("kaylah",
                "Made my grandmother's apple pie recipe for the first time. It turned out amazing! 🥧 Some traditions are worth keeping alive.", 5);
        addSeedPost("fey",
                "Reading \"Atomic Habits\" for the third time. Every read reveals something new. Highly recommend for anyone building better routines. 📚", 3);
        addSeedPost("jasline",
                "Just deployed my first Spring Boot application to production. The feeling when it works on the first try is unmatched. 🚀 #SpringBoot #Java", 2);
        addSeedPost("kaylah",
                "Sunday morning coffee and a good book — life doesn't get much better than this ☕📖", 1);
    }

    private void addSeedPost(String author, String content, long hoursAgo) {
        String[] p = profileOf(author);
        Post post = new Post(author, p[0], p[1], p[2], content, null, hoursAgo);
        posts.put(post.getId(), post);
    }

    public List<Post> getAllPosts() {
        List<Post> list = new ArrayList<>(posts.values());
        list.sort((a, b) -> Long.compare(b.getId(), a.getId()));
        return list;
    }

    public Optional<Post> getPost(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public void toggleLike(long postId, String username) {
        Post post = posts.get(postId);
        if (post != null) {
            post.toggleLike(username);
        }
    }

    public void addComment(long postId, String username, String content) {
        Post post = posts.get(postId);
        if (post != null) {
            String[] p = profileOf(username);
            Comment comment = new Comment(username, p[0], p[1], p[2], content);
            post.getComments().add(comment);
        }
    }

    public void createPost(String username, String content) {
        String[] p = profileOf(username);
        Post post = new Post(username, p[0], p[1], p[2], content, null, 0);
        posts.put(post.getId(), post);
    }

    public String[] profileOf(String username) {
        UserProfile p = getProfile(username);
        return new String[]{p.getDisplayName(), p.getAvatarInitial(), p.getAvatarColor()};
    }

    public UserProfile getProfile(String username) {
        return profiles.computeIfAbsent(username, u ->
                new UserProfile(u, u, "", "#888888"));
    }

    public void updateProfile(String username, String displayName, String bio, String avatarColor) {
        UserProfile p = getProfile(username);
        if (displayName != null && !displayName.isBlank()) p.setDisplayName(displayName);
        p.setBio(bio != null ? bio : "");
        if (avatarColor != null && avatarColor.matches("^#[0-9A-Fa-f]{6}$")) {
            p.setAvatarColor(avatarColor);
        }
    }

    public List<Post> getPostsByUser(String username) {
        return posts.values().stream()
                .filter(p -> username.equals(p.getAuthor()))
                .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                .collect(Collectors.toList());
    }
}
