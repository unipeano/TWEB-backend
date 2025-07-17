package unito.tweb.projectbackend.session;

import unito.tweb.projectbackend.dto.UserDTO;

public class SessionData {
    private final UserDTO user;
    private final String message;

    public SessionData(UserDTO user, String message) {
        this.user = user;
        this.message = message;
    }

    public UserDTO getUser() {
        return user;
    }
    public String getMessage() {
        return message;
    }


}
