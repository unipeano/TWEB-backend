package unito.tweb.projectbackend.session;

public class SessionData {
    private final String username;
    private final String role;
    private final String description;
    private final String image;
    private final String message;

    public SessionData(String username, String role, String description, String image, String message) {
        this.username = username;
        this.role = role;
        this.description = description;
        this.image = image;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
