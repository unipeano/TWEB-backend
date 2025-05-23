package unito.tweb.projectbackend.session;

public class SessionData {
    private final String username;
    private final String message;

    public SessionData(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
