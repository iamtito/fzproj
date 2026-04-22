package com.feyproj.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Post {

    private static final AtomicLong counter = new AtomicLong(1);

    private final long id;
    private final String author;
    private final String displayName;
    private final String avatarInitial;
    private final String avatarColor;
    private final String content;
    private final String imageUrl;
    private final LocalDateTime createdAt;
    private final Set<String> likedBy = new HashSet<>();
    private final List<Comment> comments = new ArrayList<>();

    public Post(String author, String displayName, String avatarInitial,
                String avatarColor, String content, String imageUrl,
                long hoursAgo) {
        this.id = counter.getAndIncrement();
        this.author = author;
        this.displayName = displayName;
        this.avatarInitial = avatarInitial;
        this.avatarColor = avatarColor;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = LocalDateTime.now().minusHours(hoursAgo);
    }

    public void toggleLike(String username) {
        if (!likedBy.remove(username)) {
            likedBy.add(username);
        }
    }

    public boolean isLikedBy(String username) {
        return likedBy.contains(username);
    }

    public int getLikeCount() {
        return likedBy.size();
    }

    public String getTimeAgo() {
        long minutes = Duration.between(createdAt, LocalDateTime.now()).toMinutes();
        if (minutes < 1) return "just now";
        if (minutes < 60) return minutes + "m ago";
        long hours = minutes / 60;
        if (hours < 24) return hours + "h ago";
        return (hours / 24) + "d ago";
    }

    public long getId() { return id; }
    public String getAuthor() { return author; }
    public String getDisplayName() { return displayName; }
    public String getAvatarInitial() { return avatarInitial; }
    public String getAvatarColor() { return avatarColor; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Set<String> getLikedBy() { return likedBy; }
    public List<Comment> getComments() { return comments; }
}
