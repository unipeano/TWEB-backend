package unito.tweb.projectbackend.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import unito.tweb.projectbackend.persistence.User;
import unito.tweb.projectbackend.persistence.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
         this.userRepository = userRepository;
    }

     @PostConstruct
     public void init() {
         userRepository.save(new User("Mattia", "Mattia", "CHEF"));
         userRepository.save(new User("Andrea", "Andrea", "CHEF"));
         userRepository.save(new User("Antonio", "Antonio", "CHEF"));
         userRepository.save(new User("Elena", "Elena", "CHEF"));
         userRepository.save(new User("Paola", "Paola", "USER"));
         userRepository.save(new User("Alessandro", "Alessandro", "USER"));
         userRepository.save(new User("Pietro", "Pietro", "USER"));
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
}
