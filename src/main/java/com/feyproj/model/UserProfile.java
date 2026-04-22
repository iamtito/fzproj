package com.feyproj.model;

public class UserProfile {

    private final String username;
    private String displayName;
    private String bio;
    private String avatarColor;

    public UserProfile(String username, String displayName, String bio, String avatarColor) {
        this.username = username;
        this.displayName = displayName;
        this.bio = bio;
        this.avatarColor = avatarColor;
    }

    public String getAvatarInitial() {
        String name = (displayName != null && !displayName.isBlank()) ? displayName : username;
        return name.substring(0, 1).toUpperCase();
    }

    public String getUsername() { return username; }
    public String getDisplayName() { return displayName; }
    public String getBio() { return bio; }
    public String getAvatarColor() { return avatarColor; }

    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public void setBio(String bio) { this.bio = bio; }
    public void setAvatarColor(String avatarColor) { this.avatarColor = avatarColor; }
}
