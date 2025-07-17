package unito.tweb.projectbackend.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import unito.tweb.projectbackend.dto.UserDTO;
import unito.tweb.projectbackend.persistence.User;
import unito.tweb.projectbackend.persistence.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
         this.userRepository = userRepository;
    }

     @PostConstruct
     public void init() {
         userRepository.save(new User("Mattia", "Mattia", "CHEF", "Chef di cucina", "1.jpg"));
         userRepository.save(new User("Andrea", "Andrea", "CHEF", "Chef di pasticceria", "3.jpg"));
         userRepository.save(new User("Antonio", "Antonio", "CHEF", "Chef di ristorante", "2.jpg"));
         userRepository.save(new User("Elena", "Elena", "CHEF", "Chef di cucina vegana", "5.jpg"));
         userRepository.save(new User("Paola", "Paola", "USER", "Appassionata di cucina", "6.jpg"));
         userRepository.save(new User("Alessandro", "Alessandro", "USER", "Appassionato di cucina italiana", "7.jpg"));
         userRepository.save(new User("Pietro", "Pietro", "USER", "Appassionato di cucina asiatica", "4.jpg"));
         userRepository.save(new User("Giorgio", "Giorgio", "ADMIN"));
         userRepository.save(new User("Francesco", "Francesco", "ADMIN"));
     }

    public boolean userDoesNotExist(String username) {
        return !this.userRepository.existsById(username);
    }

    public boolean checkCredentials(String username, String password) {
        return this.userRepository.existsByUsernameAndPassword(username, password);
    }

    public Optional<User> getUserByUsername(String username) {
        return this.userRepository.findById(username);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = this.userRepository.findAllByRoleNot("ADMIN");
        return users.stream()
                .map(user -> new UserDTO(user.getUsername(), user.getRole(), user.getDescription(), user.getImage()))
                .toList();
    }

    public UserDTO getUserDTO(User user) {
        return new UserDTO(user.getUsername(), user.getRole(), user.getDescription(), user.getImage());
    }
}
