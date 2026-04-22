# FeyProj — Social Media App

A simple social media web application built with **Java Spring Boot**. Users can log in, view a feed of posts, like and comment on posts, create new posts, and view/edit user profiles.

---

## Prerequisites

You need **Java** and **Maven** installed on your machine.

### 1. Install Java (OpenJDK)

**macOS (Homebrew):**
```bash
brew install openjdk
```

After installing, add Java to your PATH:
```bash
export JAVA_HOME=/usr/local/opt/openjdk
export PATH="$JAVA_HOME/bin:$PATH"
```

To make it permanent, add those two lines to your `~/.zshrc` or `~/.bash_profile`:
```bash
echo 'export JAVA_HOME=/usr/local/opt/openjdk' >> ~/.zshrc
echo 'export PATH="$JAVA_HOME/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

**Verify:**
```bash
java --version
```

---

### 2. Install Maven

**macOS (Homebrew):**
```bash
brew install maven
```

**Linux (apt):**
```bash
sudo apt update && sudo apt install -y maven
```

**Windows:**
Download from https://maven.apache.org/download.cgi and follow the installer instructions.

**Verify:**
```bash
mvn --version
```

---

## Running the Application

1. **Clone or extract the project** into a folder of your choice.

2. **Navigate to the project directory:**
   ```bash
   cd feyproj
   ```

3. **Set JAVA_HOME** (macOS/Linux, if not already in your shell profile):
   ```bash
   export JAVA_HOME=/usr/local/opt/openjdk
   ```

4. **Start the application:**
   ```bash
   mvn spring-boot:run
   ```

5. **Open your browser** and go to:
   ```
   http://localhost:3080
   ```

---

## Demo Accounts

Log in with any of the following built-in accounts (no database required — everything runs in memory):

| Username  | Password      |
|-----------|---------------|
| alice     | password123   |
| bob       | password123   |
| charlie   | password123   |

> **Note:** All data (posts, likes, comments, profile edits) is stored in memory and resets when the server restarts.

---

## Features

- **Login / Logout** — secure form-based authentication
- **Feed** — view all posts from all users, sorted newest first
- **Like posts** — toggle likes on any post
- **Comment on posts** — add comments to any post
- **Create posts** — write and publish your own posts (up to 1000 characters)
- **User profiles** — click any author name to view their profile page
- **Edit profile** — change your display name, bio, and avatar color

---

## Tech Stack

| Layer       | Technology                     |
|-------------|-------------------------------|
| Language    | Java 17                        |
| Framework   | Spring Boot 3.2.0              |
| Security    | Spring Security (in-memory)    |
| Templates   | Thymeleaf                      |
| Build tool  | Maven 3.x                      |
| Storage     | In-memory (`ConcurrentHashMap`)|

---

## Project Structure

```
feyproj/
├── pom.xml                          # Maven project descriptor
└── src/
    └── main/
        ├── java/com/feyproj/
        │   ├── FeyProjApplication.java      # App entry point
        │   ├── config/
        │   │   └── SecurityConfig.java      # Auth & user setup
        │   ├── controller/
        │   │   ├── FeedController.java      # Feed, like, comment routes
        │   │   └── ProfileController.java   # Profile view & edit routes
        │   ├── model/
        │   │   ├── Comment.java
        │   │   ├── Post.java
        │   │   └── UserProfile.java
        │   └── service/
        │       └── PostService.java         # Business logic & seed data
        └── resources/
            ├── application.properties       # Port (3080), app settings
            ├── static/css/
            │   └── style.css
            └── templates/
                ├── login.html
                ├── feed.html
                ├── profile.html
                └── profile-edit.html
```

---

## Stopping the Application

Press **Ctrl + C** in the terminal where the app is running.

If port 3080 is already in use on the next start, run:
```bash
lsof -ti:3080 | xargs kill -9
```
Then start again with `mvn spring-boot:run`.
