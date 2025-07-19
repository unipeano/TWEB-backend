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
    private final RecipeBookService recipeBookService;
    public RecipeService(RecipeRepository recipeRepository,
                         RecipeIngredientRepository recipeIngredientRepository,
                         IngredientRepository ingredientRepository,
                         CategoryRepository categoryRepository,
                         RecipeCategoryRepository recipeCategoryRepository,
                         RecipeBookService recipeBookService) {
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
        this.recipeCategoryRepository = recipeCategoryRepository;
        this.recipeBookService = recipeBookService;
    }

    @PostConstruct
    public void init() {
        Category breakfast = saveCategory("Breakfast");
        Category mainCourse = saveCategory("Main Course");
        Category appetizer = saveCategory("Appetizer");
        Category dessert = saveCategory("Dessert");

        Ingredient tomato = saveIngredient("Tomato");
        Ingredient tomatoSauce = saveIngredient("Tomato sauce");
        Ingredient spaghetti = saveIngredient("Spaghetti");
        Ingredient basil = saveIngredient("Basil");
        Ingredient oliveOil = saveIngredient("Olive Oil");
        Ingredient salt = saveIngredient("Salt");
        Ingredient garlic = saveIngredient("Garlic");
        Ingredient sugar = saveIngredient("Sugar");
        Ingredient flour = saveIngredient("Flour");
        Ingredient egg = saveIngredient("Egg");
        Ingredient cheese = saveIngredient("Cheese");
        Ingredient pasta = saveIngredient("Pasta");
        Ingredient chicken = saveIngredient("Chicken");
        Ingredient lettuce = saveIngredient("Lettuce");
        Ingredient bread = saveIngredient("Bread");
        Ingredient bacon = saveIngredient("Bacon");

        Recipe spaghettiTomato = saveRecipe("Spaghetti with tomato sauce", "A classic Italian pasta dish with tomato sauce", "1.png",
                "Boil pasta, Prepare sauce, Combine all", 10, 4, "Mattia");
        saveRecipeCategory(spaghettiTomato.getId(), mainCourse.getId());
        saveRecipeIngredient(spaghettiTomato.getId(), tomatoSauce.getId(), "800g");
        saveRecipeIngredient(spaghettiTomato.getId(), spaghetti.getId(), "320g");
        saveRecipeIngredient(spaghettiTomato.getId(), basil.getId(), "a.d.");
        saveRecipeIngredient(spaghettiTomato.getId(), oliveOil.getId(), "30g");
        saveRecipeIngredient(spaghettiTomato.getId(), salt.getId(), "a.d.");
        saveRecipeIngredient(spaghettiTomato.getId(), garlic.getId(), "1 clove");

        Recipe cake = saveRecipe("Chocolate Cake", "Rich dark chocolate cake", "2.png",
                "Mix ingredients, Pour batter, Bake cake", 45, 8, "Pietro");
        saveRecipeCategory(cake.getId(), dessert.getId());
        saveRecipeIngredient(cake.getId(), sugar.getId(), "100g");
        saveRecipeIngredient(cake.getId(), flour.getId(), "200g");
        saveRecipeIngredient(cake.getId(), egg.getId(), "3");

        Recipe salad = saveRecipe("Caesar Salad", "Fresh salad with chicken and dressing", "3.png",
                "Grill chicken, Toss lettuce, Add cheese", 20, 2, "Andrea");
        saveRecipeCategory(salad.getId(), appetizer.getId());
        saveRecipeIngredient(salad.getId(), lettuce.getId(), "100g");
        saveRecipeIngredient(salad.getId(), chicken.getId(), "150g");
        saveRecipeIngredient(salad.getId(), cheese.getId(), "30g");

        Recipe pancakes = saveRecipe("Pancakes", "Fluffy morning pancakes", "4.png",
                "Mix batter, Heat pan, Fry pancakes", 25, 3, "Antonio");
        saveRecipeCategory(pancakes.getId(), breakfast.getId());
        saveRecipeCategory(pancakes.getId(), dessert.getId());
        saveRecipeIngredient(pancakes.getId(), flour.getId(), "150g");
        saveRecipeIngredient(pancakes.getId(), egg.getId(), "2");
        saveRecipeIngredient(pancakes.getId(), sugar.getId(), "50g");

        Recipe omelette = saveRecipe("Omelette", "Quick and easy egg dish", "5.png",
                "Beat eggs, Add cheese, Cook omelette", 10, 1, "Paola");
        saveRecipeCategory(omelette.getId(), breakfast.getId());
        saveRecipeIngredient(omelette.getId(), egg.getId(), "3");
        saveRecipeIngredient(omelette.getId(), cheese.getId(), "40g");

        Recipe blt = saveRecipe("BLT Sandwich", "Bacon, Lettuce, Tomato sandwich", "6.png",
                "Toast bread, Cook bacon, Assemble sandwich", 10, 1, "Elena");
        saveRecipeCategory(blt.getId(), mainCourse.getId());
        saveRecipeCategory(blt.getId(), appetizer.getId());
        saveRecipeIngredient(blt.getId(), bacon.getId(), "3 slices");
        saveRecipeIngredient(blt.getId(), lettuce.getId(), "20g");
        saveRecipeIngredient(blt.getId(), tomato.getId(), "2 slices");
        saveRecipeIngredient(blt.getId(), bread.getId(), "2 slices");

        Recipe lampredotto = saveRecipe("Lampredotto", "Traditional Florentine sandwich made with cow's stomach", "7.png",
                "Boil lampredotto, Season well, Fill bread", 60, 1, "Elena");
        saveRecipeCategory(lampredotto.getId(), mainCourse.getId());
        saveRecipeCategory(lampredotto.getId(), appetizer.getId());
        saveRecipeIngredient(lampredotto.getId(), bread.getId(), "1 roll");
        saveRecipeIngredient(lampredotto.getId(), salt.getId(), "a.d.");
        saveRecipeIngredient(lampredotto.getId(), oliveOil.getId(), "10g");

        Recipe muffins = saveRecipe("Muffins", "Soft and sweet baked breakfast treat", "8.png",
                "Mix ingredients, Fill molds, Bake muffins", 30, 6, "Paola");
        saveRecipeCategory(muffins.getId(), breakfast.getId());
        saveRecipeCategory(muffins.getId(), dessert.getId());
        saveRecipeIngredient(muffins.getId(), flour.getId(), "200g");
        saveRecipeIngredient(muffins.getId(), egg.getId(), "2");
        saveRecipeIngredient(muffins.getId(), sugar.getId(), "80g");
        saveRecipeIngredient(muffins.getId(), oliveOil.getId(), "50g");


    }

    private Category saveCategory(String name) {
        return categoryRepository.save(new Category(name));
    }

    private Ingredient saveIngredient(String name) {
        return ingredientRepository.save(new Ingredient(name));
    }

    private Recipe saveRecipe(String title, String description, String image, String instructions,
                              Integer prepTime, Integer servings, String author) {
        return recipeRepository.save(new Recipe(title, description, image, instructions, prepTime, servings, author));
    }

    private void saveRecipeCategory(Integer recipeId, Integer categoryId) {
        recipeCategoryRepository.save(new RecipeCategory(recipeId, categoryId));
    }

    private void saveRecipeIngredient(Integer recipeId, Integer ingredientId, String quantity) {
        recipeIngredientRepository.save(new RecipeIngredient(recipeId, ingredientId, quantity));
    }

    @Transactional
    public RecipeDTO addRecipe(RecipeDTO request) {
        if(!this.searchRecipeByAuthorTitle(request.getAuthor(), request.getTitle()).isEmpty()) {
            throw new IllegalArgumentException("A recipe with the same title by the same author already exists.");
        }
        Recipe recipe = saveBasicRecipe(request);
        recipeBookService.addRecipeToDefaultRecipeBook(request.getAuthor(), recipe.getId());
        saveIngredientsToRecipe(recipe.getId(), request.getIngredients());
        saveCategoriesToRecipe(recipe.getId(), request.getCategories());
        return convertToDTO(recipe);
    }

    private Recipe saveBasicRecipe(RecipeDTO request) {
        Recipe recipe = new Recipe(
                request.getTitle(),
                request.getDescription(),
                request.getImage(),
                request.getInstructions(),
                request.getPreparationTime(),
                request.getServings(),
                request.getAuthor()
        );
        return recipeRepository.save(recipe);
    }

    private void saveIngredientsToRecipe(Integer recipeId, List<IngredientDTO> ingredients) {
        ingredients.forEach(ingredient -> recipeIngredientRepository.save(new RecipeIngredient(recipeId, ingredient.getId(), ingredient.getQuantity())));
    }

    // client fetcha le categories
    private void saveCategoriesToRecipe(Integer recipeId, List<Category> categories) {
        categories.forEach(category -> recipeCategoryRepository.save(new RecipeCategory(recipeId, category.getId())));
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

    @Transactional
    public void deleteRecipe(Integer id) {
        // Delete recipe ingredients
        this.recipeIngredientRepository.deleteAllByRecipeId(id);
        // Delete recipe categories
        this.recipeCategoryRepository.deleteAllByRecipeId(id);
        // Remove recipe from users' recipe books
        this.recipeBookService.removeRecipe(id);
        // Delete the recipe itself
        this.recipeRepository.deleteById(id);
    }

    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    public List<Ingredient> getIngredients() {
        return this.ingredientRepository.findAll();
    }

    public List<Recipe> searchRecipeByCategory(String category) {
        Optional<Integer> categoryId = this.categoryRepository.findIdByName(category);
        if (categoryId.isEmpty()) {
            return List.of();
        }
        return this.recipeCategoryRepository.findByCategoryId(categoryId.get()).stream()
                .map(RecipeCategory::getRecipeId)
                .map(this.recipeRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

    }

    public boolean categoryExists(String category) {
        return this.categoryRepository.existsByNameIgnoreCase(category);
    }

    public List<IngredientDTO> getIngredientsByRecipeId(Integer id) {
        return this.recipeIngredientRepository.findByRecipeId(id).stream()
                .map(ri -> this.ingredientRepository.findById(ri.getIngredientId())
                        .map(ingredient -> new IngredientDTO(ingredient.getId(), ingredient.getName(), ri.getQuantity())))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        // se non trova l'ingrediente lo ignora (no exception)
    }

    public List<Category> getCategoriesByRecipeId(Integer id) {
        return this.recipeCategoryRepository.findByRecipeId(id).stream()
                .map(rc -> this.categoryRepository.findById(rc.getCategoryId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        // se non trova la categoria la ignora (no exception)
    }

    private RecipeDTO convertToDTO(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO(recipe);
        dto.setIngredients(getIngredientsByRecipeId(recipe.getId()));
        dto.setCategories(getCategoriesByRecipeId(recipe.getId()));
        return dto;
    }

    private List<RecipeDTO> convertRecipes(List<Recipe> recipes) {
        return recipes.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<RecipeDTO> getAllRecipeDTOs() {
        List<Recipe> recipes = getAllRecipes();
        return convertRecipes(recipes);
    }

    public List<RecipeDTO> getRecipesByTitle(String title) {
        List<Recipe> recipes = searchRecipeByTitle(title);
        return convertRecipes(recipes);
    }

    public List<RecipeDTO> getRecipesByAuthor(String author) {
        List<Recipe> recipes = searchRecipeByAuthor(author);
        return convertRecipes(recipes);
    }

    public List<RecipeDTO> getRecipesByAuthorAndTitle(String author, String title) {
        List<Recipe> recipes = searchRecipeByAuthorTitle(author, title);
        return convertRecipes(recipes);
    }
    public List<RecipeDTO> getDTOsByCategory(String category) {
        List<Recipe> recipes = searchRecipeByCategory(category);
        return recipes.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public RecipeDTO getRecipeDTO(Recipe recipe) {
        return convertToDTO(recipe);
    }

    public List<RecipeDTO> getDTOs(List<Recipe> recipes) {
        return convertRecipes(recipes);
    }
}
