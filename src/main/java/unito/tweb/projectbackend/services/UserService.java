package unito.tweb.projectbackend.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import unito.tweb.projectbackend.persistence.User;
import unito.tweb.projectbackend.persistence.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
         this.userRepository = userRepository;
    }

     @PostConstruct
     public void init() {
         userRepository.save(new User("Mattia", "Peano", "CHEF"));
         userRepository.save(new User("Pietro", "Pietro", "USER"));
         userRepository.save(new User("Giorgio", "Gallina", "ADMIN"));
     }

    public boolean userDoesNotExist(String username) {
        return false; // TODO: Implement this method
    }

    public boolean checkCredentials(String username, String password) {
        return this.userRepository.existsByUsernameAndPassword(username, password);
    }
}
