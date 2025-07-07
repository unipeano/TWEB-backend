package unito.tweb.projectbackend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unito.tweb.projectbackend.dto.RecipeDTO;
import unito.tweb.projectbackend.persistence.Recipe;
import unito.tweb.projectbackend.persistence.User;
import unito.tweb.projectbackend.services.RecipeService;
import unito.tweb.projectbackend.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class RecipeController {
    RecipeService recipeService;
    UserService userService;

    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }


    @GetMapping("/recipes/{id}")
    public ResponseEntity<RecipeDTO> recipeById(@PathVariable Integer id) {
        Optional<Recipe> recipe = recipeService.getRecipeById(id);
        if (recipe.isPresent()) {
            RecipeDTO recipeDTO = new RecipeDTO(recipe.get());
            recipeDTO.setIngredients(recipeService.getIngredientsByRecipeId(id));
            recipeDTO.setCategories(recipeService.getCategoriesByRecipeId(id));
            return ResponseEntity.ok(recipeDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> recipes(@RequestParam(required = false) String author,
                                            @RequestParam(required = false) String title) {
        if (author != null && title != null) {
            return ResponseEntity.ok(recipeService.searchRecipeByAuthorTitle(author, title));
        }
        if (author != null) {
            return ResponseEntity.ok(recipeService.searchRecipeByAuthor(author));
        }
        if (title != null) {
            return ResponseEntity.ok(recipeService.searchRecipeByTitle(title));
        }
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    // ricordarsi degli ID (server)
    @PostMapping("/recipes")
    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeDTO recipe) {
        return ResponseEntity.ok(recipeService.addRecipe(recipe));
        // posso anche restituire solo l'id da aggiornare?
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Integer id, HttpSession session) {
        Optional<Recipe> recipe = recipeService.getRecipeById(id);
        String username = (String) session.getAttribute("username");
        Optional<User> user = userService.getUserByUsername(username);

        if (recipe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (user.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        if (!recipe.get().getAuthor().equals(username) && !user.get().isAdmin()) {
            return ResponseEntity.status(403).build();
        }

        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/recipes/categories")
    public ResponseEntity<List<String>> categories() {
        return ResponseEntity.ok(recipeService.getCategories());
    }


    @GetMapping("/recipes/category/{category}")
    public ResponseEntity<List<Recipe>> recipesByCategory(@PathVariable String category) {
        String decodedCategory = category.replace("-", " ");
        if (!recipeService.categoryExists(decodedCategory)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipeService.searchRecipeByCategory(decodedCategory));
    }
}
