package entity;

import java.util.UUID;

public class User {
    private String id;  // Changed from int to String to store UUID
    private String username;
    private String email;
    private String password;
    private String role;

    public User(String username, String email, String password, String role) {
        this.id = UUID.randomUUID().toString();  // Generate UUID string instead of using counter
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getId() {  // Updated return type to String
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
    public String getRole() {
        return this.role;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }

    // Rest of the class remains the same
}