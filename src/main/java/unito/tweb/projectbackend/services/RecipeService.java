package unito.tweb.projectbackend.services;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import unito.tweb.projectbackend.dto.IngredientDTO;
import unito.tweb.projectbackend.dto.RecipeDTO;
import unito.tweb.projectbackend.persistence.*;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeCategoryRepository recipeCategoryRepository;
    private final UserRepository userRepository;
    public RecipeService(RecipeRepository recipeRepository,
                         RecipeIngredientRepository recipeIngredientRepository,
                         IngredientRepository ingredientRepository,
                         CategoryRepository categoryRepository,
                         RecipeCategoryRepository recipeCategoryRepository,
                         UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
        this.recipeCategoryRepository = recipeCategoryRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        Recipe r1 = new Recipe("Spaghetti al Pomodoro", "A classic Italian pasta dish with tomato sauce", "1.png", "Cook pasta, make sauce, combine.", 10, 4, "Mattia");
        //Recipe r2 = new Recipe("Pasta al Pesto", "A simple pasta dish with pesto sauce", "2.png", "Boil pasta, make sauce, combine.", 15, "Mattia");
        //Recipe r3 = new Recipe("Pizza Margherita", "A classic pizza with tomato, mozzarella, and basil", "3.png", "Prepare dough, add toppings, bake.", 20, "Pietro");
        //Recipe r4 = new Recipe("Risotto alla Milanese", "A creamy risotto with saffron", "4.png", "Cook rice, add broth, stir in saffron.", 30, "Pietro");

        this.saveRecipe(r1, new String[]{"Tomato", "Spaghetti", "Basil", "Olive Oil", "Salt", "Garlic"},
                new String[]{"800g", "320g", "a.d.", "30g", "a.d.", "1 clove"});

        Category c1 = new Category("Appetizer");
        Category c2 = new Category("Main Course");
        Category c3 = new Category("Dessert");
        this.categoryRepository.save(c1);
        this.categoryRepository.save(c2);
        this.categoryRepository.save(c3);
        this.recipeCategoryRepository.save(new RecipeCategory(r1.getId(), c2.getId()));


    }

    private void saveRecipe(Recipe recipe, String[] ingredients, String[] quantities) {
        this.recipeRepository.save(recipe);
        for (int i = 0; i < ingredients.length; i++) {
            int finalI = i;
            Ingredient ingredient = this.ingredientRepository.findByName(ingredients[i])
                    .orElseGet(() -> this.ingredientRepository.save(new Ingredient(ingredients[finalI])));
            this.recipeIngredientRepository.save(new RecipeIngredient(recipe.getId(), ingredient.getId(), quantities[i]));
        }

    }

    @Transactional
    public Recipe addRecipe(RecipeDTO request) {

        Recipe recipe = new Recipe(
                request.getTitle(),
                request.getDescription(),
                request.getImage(),
                request.getInstructions(),
                request.getPreparationTime(),
                request.getServings(),
                request.getAuthor()
        );
        recipeRepository.save(recipe);

        for (IngredientDTO ingredientDTO : request.getIngredients()) {
            Ingredient ingredient = ingredientRepository.findByName(ingredientDTO.getName())
                    .orElseGet(() -> ingredientRepository.save(new Ingredient(ingredientDTO.getName())));
            // probabilmente non serve il check su null, ma lo metto per sicurezza

            recipeIngredientRepository.save(new RecipeIngredient(recipe.getId(), ingredient.getId(), ingredientDTO.getQuantity()));
        }

        for (String categoryName : request.getCategories()) {
            Category category = categoryRepository.findByName(categoryName)
                    .orElseGet(() -> categoryRepository.save(new Category(categoryName)));

            recipeCategoryRepository.save(new RecipeCategory(recipe.getId(), category.getId()));
        }

        return recipe;
    }



    public Optional<Recipe> getRecipeById(Integer id) {
        return this.recipeRepository.findById(id);
    }

    public List<Recipe> getAllRecipes() {
        return this.recipeRepository.findAll();
    }

    public List<Recipe> searchRecipeByTitle(String title) {
        return this.recipeRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Recipe> searchRecipeByAuthorTitle(String author, String title) {
        return this.recipeRepository.findByAuthorContainingAndTitleContainingAllIgnoreCase(author, title);
    }

    public List<Recipe> searchRecipeByAuthor(String author) {
    return this.recipeRepository.findByAuthorContainingIgnoreCase(author);
    }
}
