package com.feyproj.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class Comment {

    private static final AtomicLong counter = new AtomicLong(1);

    private final long id;
    private final String author;
    private final String displayName;
    private final String avatarInitial;
    private final String avatarColor;
    private final String content;
    private final LocalDateTime createdAt;

    public Comment(String author, String displayName, String avatarInitial,
                   String avatarColor, String content) {
        this.id = counter.getAndIncrement();
        this.author = author;
        this.displayName = displayName;
        this.avatarInitial = avatarInitial;
        this.avatarColor = avatarColor;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public String getTimeAgo() {
        long minutes = Duration.between(createdAt, LocalDateTime.now()).toMinutes();
        if (minutes < 1) return "just now";
        if (minutes < 60) return minutes + "m ago";
        return (minutes / 60) + "h ago";
    }

    public long getId() { return id; }
    public String getAuthor() { return author; }
    public String getDisplayName() { return displayName; }
    public String getAvatarInitial() { return avatarInitial; }
    public String getAvatarColor() { return avatarColor; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
