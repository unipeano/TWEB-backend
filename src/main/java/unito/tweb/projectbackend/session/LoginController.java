package unito.tweb.projectbackend.session;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unito.tweb.projectbackend.persistence.User;
import unito.tweb.projectbackend.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/session")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class LoginController {
    private final UserService userService;

    LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<SessionData> create(
            HttpSession session,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) {
        String existingUser = (String) session.getAttribute("username");
        if (username == null || password == null) {
            if (existingUser == null) {
                return ResponseEntity.ok(new SessionData("", null,
                        null, null,
                        "User not authenticated."));
            }
            Optional<User> userOpt = userService.getUserByUsername(existingUser);
            if (userOpt.isEmpty()) {
                session.invalidate();
                return ResponseEntity.status(404).body(new SessionData("", null, null, null,
                        "User not found in the database."));
            }
            User user = userOpt.get();
            return ResponseEntity.ok(new SessionData(existingUser,
                    user.getRole(),
                    user.getDescription(),
                    user.getImage(),
                    "User authenticated."));
        }
        if (existingUser != null) {
            if (username.equals(existingUser)) {
                Optional<User> userOpt = userService.getUserByUsername(existingUser);
                if(userOpt.isEmpty()) {
                    session.invalidate();
                    return ResponseEntity.status(404).body(new SessionData("", null, null, null,
                            "User not found in the database."));
                }
                User user = userOpt.get();
                return ResponseEntity.ok(new SessionData(username,
                        user.getRole(),
                        user.getDescription(),
                        user.getImage(),
                        "User already authenticated."));
            }
            return ResponseEntity.badRequest().body(new SessionData("",
                    null, null, null,
                    "Another user authenticated."));
        }
        boolean auth = userService.checkCredentials(username, password);
        if (auth) {
            Optional<User> userOpt = userService.getUserByUsername(username);

            if(userOpt.isEmpty()){
                return ResponseEntity.status(404).body(new SessionData("", null, null, null,
                        "User not found in the database."));
            }
            User user = userOpt.get();
            session.setAttribute("username", username);
            session.setAttribute("role", user.getRole());
            return ResponseEntity.ok(new SessionData(
                    username,
                    user.getRole(),
                    user.getDescription(),
                    user.getImage(),
                    "Log in successful."
            ));
        }
        return ResponseEntity.status(401).body(
                new SessionData("", null, null, null,
                        "Invalid credentials."));
    }

    @GetMapping("/logout")
    public ResponseEntity<SessionData> invalidate(HttpSession session) {
        String existingUser = (String) session.getAttribute("username");
        if (existingUser == null) {
            return ResponseEntity.ok(new SessionData("", null, null, null,
                    "No user to log out."));
        }
        session.invalidate();
        return ResponseEntity.ok(new SessionData("", null, null, null,
                "User successfully logged out."));
    }
}
