package entity;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String role;

    // Counter for generating unique IDs
    private static int idCounter = 1;

    public User(String username, String email, String password, String role) {
        this.id = idCounter++;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
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

}