package unito.tweb.projectbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unito.tweb.projectbackend.dto.RecipeDTO;
import unito.tweb.projectbackend.persistence.Recipe;
import unito.tweb.projectbackend.services.RecipeService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class RecipeController {
    RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> recipeById(@PathVariable Integer id) {
        Optional<Recipe> recipe = recipeService.getRecipeById(id);
        if (recipe.isPresent()) {
            return ResponseEntity.ok(recipe.get());
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

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeDTO recipe) {
        return ResponseEntity.ok(recipeService.addRecipe(recipe));
    }

}
