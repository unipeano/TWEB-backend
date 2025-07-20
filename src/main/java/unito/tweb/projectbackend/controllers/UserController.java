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

    @PostMapping("/users/delete")
    public ResponseEntity<List<UserDTO>> deleteUser(@RequestBody UserDTO user, HttpSession session) {
        String currentUsername = (String) session.getAttribute("username");
        Optional<User> userOpt = userService.getUserByUsername(currentUsername);
        if (userOpt.isEmpty() || !userOpt.get().isAdmin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (userService.userDoesNotExist(user.getUsername())) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(user.getUsername());
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{username}/role")
    public ResponseEntity<UserDTO> changeUserRole(@PathVariable String username, HttpSession session) {
        String currentUsername = (String) session.getAttribute("username");
        Optional<User> userOpt = userService.getUserByUsername(currentUsername);
        if (userOpt.isEmpty() || !userOpt.get().isAdmin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (userService.userDoesNotExist(username)) {
            return ResponseEntity.notFound().build();
        }
        User updatedUser = userService.changeUserRole(username);
        return ResponseEntity.ok(userService.getUserDTO(updatedUser));
    }

}
