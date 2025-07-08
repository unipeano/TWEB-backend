package unito.tweb.projectbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import unito.tweb.projectbackend.persistence.RecipeBook;
import unito.tweb.projectbackend.services.RecipeBookService;
import unito.tweb.projectbackend.services.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class RecipeBookController {

    RecipeBookService recipeBookService;
    UserService userService;

    public RecipeBookController(RecipeBookService recipeBookService,
                                UserService userService) {
        this.recipeBookService = recipeBookService;
        this.userService = userService;
    }


    @GetMapping("/users/{username}/recipebooks")
    public ResponseEntity<List<RecipeBook>> recipeBooks(@PathVariable String username) {
        if (userService.userDoesNotExist(username)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(recipeBookService.getRecipeBooksForUser(username));
    }


}
