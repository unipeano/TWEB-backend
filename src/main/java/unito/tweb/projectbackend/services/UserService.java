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
         userRepository.save(new User("Mattia", "Mattia", "CHEF", "Chef esperto in cucina italiana e primi piatti", "1.jpg"));
         userRepository.save(new User("Andrea", "Andrea", "CHEF", "Chef specializzato in piatti freschi e insalate", "3.jpg"));
         userRepository.save(new User("Antonio", "Antonio", "CHEF", "Chef con talento per la cucina internazionale e breakfast", "2.jpg"));
         userRepository.save(new User("Elena", "Elena", "CHEF", "Chef innovativa, esperta in street food e panini", "5.jpg"));
         userRepository.save(new User("Paola", "Paola", "USER", "Appassionata di cucina casalinga e baking", "6.jpg"));
         userRepository.save(new User("Alessandro", "Alessandro", "USER", "Food enthusiast, in esplorazione culinaria", "7.jpg"));
         userRepository.save(new User("Pietro", "Pietro", "USER", "Appassionato di cucina etnica e sapori orientali", "4.jpg"));
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
