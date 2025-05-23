package unito.tweb.projectbackend.session;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unito.tweb.projectbackend.services.UserService;

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
                return ResponseEntity.ok(new SessionData("",
                        "User not authenticated."));
            }
            return ResponseEntity.ok(new SessionData(existingUser,
                    "User authenticated."));
        }
        if (existingUser != null) {
            if (username.equals(existingUser)) {
                return ResponseEntity.ok(new SessionData(username,
                        "User already authenticated."));
            }
            return ResponseEntity.badRequest().body(new SessionData("",
                    "Another user authenticated."));
        }
        boolean auth = userService.checkCredentials(username, password);
        if (auth) {
            session.setAttribute("username", username);
            return ResponseEntity.ok(new SessionData(
                    username,
                    "Log in successful."
            ));
        }
        return ResponseEntity.status(401).body(
                new SessionData("",
                        "Invalid credentials."));
    }

    @GetMapping("/logout")
    public ResponseEntity<SessionData> invalidate(HttpSession session) {
        String existingUser = (String) session.getAttribute("username");
        if (existingUser == null) {
            return ResponseEntity.ok(new SessionData("",
                    "No user to log out."));
        }
        session.invalidate();
        return ResponseEntity.ok(new SessionData("",
                "User successfully logged out."));
    }
}
