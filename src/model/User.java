package model;

public class User {
    private String userId;
    private String name;
    private String contact;
    private String email;
    private String password;

    public User(String userId, String name, String contact, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public static boolean login(String email, String password) {
        // this is simulated
        return true;
    }

    public static boolean register(User user) {
        // this is also simulated
        return true;
    }
}