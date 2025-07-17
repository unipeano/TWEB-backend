package unito.tweb.projectbackend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unito.tweb.projectbackend.dto.UserDTO;
import unito.tweb.projectbackend.persistence.User;
import unito.tweb.projectbackend.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> users(HttpSession session) {
        String username = (String) session.getAttribute("username");
        Optional<User> userOpt = userService.getUserByUsername(username);
        if(userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOpt.get();
        if(!user.isAdmin()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<UserDTO> user(@PathVariable String username) {
        Optional<User> userOpt = userService.getUserByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.getUserDTO(userOpt.get()));
    }

}
