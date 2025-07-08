package unito.tweb.projectbackend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeDTO recipe, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (!recipe.getAuthor().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            Recipe newRecipe = recipeService.addRecipe(recipe);
            return ResponseEntity.ok(newRecipe); // posso anche restituire solo l'id da aggiornare?
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Integer id, HttpSession session) {
        Optional<Recipe> recipe = recipeService.getRecipeById(id);
        String username = (String) session.getAttribute("username");
        Optional<User> user = userService.getUserByUsername(username);

        if (recipe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // in teoria non serve fare il check su user perchè ci pensa il filter a bloccare le richieste non autenticate
        // in caso di autenticazione invece, il logincontroller mette l'username (controllato) nella sessione

        if (!recipe.get().getAuthor().equals(username) && !user.get().isAdmin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/recipes/categories")
    public ResponseEntity<List<String>> categories() {
        return ResponseEntity.ok(recipeService.getCategories());
    }

    @GetMapping("/recipes/ingredients")
    public ResponseEntity<List<String>> ingredients() {
        return ResponseEntity.ok(recipeService.getIngredients());
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
