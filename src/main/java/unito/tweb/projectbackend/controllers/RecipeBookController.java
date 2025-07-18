package unito.tweb.projectbackend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unito.tweb.projectbackend.persistence.Recipe;
import unito.tweb.projectbackend.persistence.RecipeBook;
import unito.tweb.projectbackend.services.RecipeBookService;
import unito.tweb.projectbackend.services.UserService;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/me/recipebooks")
    public ResponseEntity<List<RecipeBook>> myRecipeBooks(HttpSession session) {
        String username = (String) session.getAttribute("username");
        return ResponseEntity.ok(recipeBookService.getRecipeBooksForUser(username));
    }


    @GetMapping("/users/{username}/recipebooks")
    public ResponseEntity<List<RecipeBook>> recipeBooks(@PathVariable String username, @RequestParam (required = false) String name) {
        if (userService.userDoesNotExist(username)) return ResponseEntity.notFound().build();
        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok(recipeBookService.searchRecipeBookByNameRecipeBookOwner(name, username));
        }
        return ResponseEntity.ok(recipeBookService.getRecipeBooksForUser(username));
    }

    @PostMapping("me/recipebooks")
    public ResponseEntity<RecipeBook> createRecipeBook(@RequestBody RecipeBook recipeBook, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (!recipeBook.getRecipeBookOwner().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            RecipeBook createdRecipeBook = recipeBookService.createRecipeBook(recipeBook.getName(), username);
            return ResponseEntity.ok(createdRecipeBook);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            // si potrebbe scrivere nel body un messaggio di errore più specifico
        }
    }

    @PostMapping("/me/recipebooks/{recipeBookId}/recipes")
    public ResponseEntity<Void> addRecipeToRecipeBook(@PathVariable Integer recipeBookId, @RequestBody Recipe recipe, HttpSession session) {
        String username = (String) session.getAttribute("username");
        Optional<String> recipeBookOwner = recipeBookService.getRecipeBookOwner(recipeBookId);
        if (recipeBookOwner.isEmpty() || !recipeBookService.recipeExists(recipe.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if(!recipeBookOwner.get().equals(username)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            recipeBookService.addRecipeToRecipeBook(recipeBookId, recipe.getId());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // forse restituire DTO?
    @GetMapping("/users/{username}/recipebooks/{recipeBookName}/recipes")
    public ResponseEntity<List<Recipe>> getRecipesInRecipeBook(@PathVariable String username, @PathVariable String recipeBookName) {
        Optional<RecipeBook> recipeBook = recipeBookService.findRecipeBookByNameAndOwner(recipeBookName, username);
        if (recipeBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Recipe> recipes = recipeBookService.getRecipesInRecipeBook(recipeBook.get().getId());
        return ResponseEntity.ok(recipes);
    }


    @GetMapping("/users/recipebooks")
    public ResponseEntity<List<RecipeBook>> getAllRecipeBooks() {
        return ResponseEntity.ok(recipeBookService.getAllRecipeBooks());
    }


}
