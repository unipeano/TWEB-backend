package unito.tweb.projectbackend.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import unito.tweb.projectbackend.persistence.*;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeBookService {

    private final RecipeBookRepository recipeBookRepository;
    private final RecipeBookRecipeRepository recipeBookRecipeRepository;
    private final RecipeRepository recipeRepository;

    public RecipeBookService(RecipeBookRecipeRepository recipeBookRecipeRepository,
                         RecipeBookRepository recipeBookRepository,
                             RecipeRepository recipeRepository) {
        this.recipeBookRecipeRepository = recipeBookRecipeRepository;
        this.recipeBookRepository = recipeBookRepository;
        this.recipeRepository = recipeRepository;
    }



    @PostConstruct
    public void init() {
        this.createRecipeBook("My recipes", "Mattia");
        this.createRecipeBook("Favorites", "Mattia");
        this.createRecipeBook("My recipes", "Pietro");
        this.createRecipeBook("My recipes", "Andrea");
        this.createRecipeBook("My recipes", "Antonio");
        this.createRecipeBook("My recipes", "Elena");
        this.createRecipeBook("My recipes", "Paola");
        this.createRecipeBook("My recipes", "Alessandro");
        this.recipeBookRecipeRepository.save(new RecipeBookRecipe(1, 1));
        this.recipeBookRecipeRepository.save(new RecipeBookRecipe(3, 2));
        this.recipeBookRecipeRepository.save(new RecipeBookRecipe(4, 3));
        this.recipeBookRecipeRepository.save(new RecipeBookRecipe(5, 4));
        this.recipeBookRecipeRepository.save(new RecipeBookRecipe(7, 5));
        this.recipeBookRecipeRepository.save(new RecipeBookRecipe(6, 6));

    }

    public RecipeBook createRecipeBook(String name, String recipeBookOwner) {
        if(!this.searchRecipeBookByNameRecipeBookOwner(name, recipeBookOwner).isEmpty()) {
            throw new IllegalArgumentException("A recipe book with the same name by the same owner already exists.");
        }
        RecipeBook recipeBook = new RecipeBook(name, recipeBookOwner);
        return recipeBookRepository.save(recipeBook);
    }


    public void removeRecipe(Integer recipeId) {
        this.recipeBookRecipeRepository.deleteAllByRecipeId(recipeId);
    }

    public List<RecipeBook> getRecipeBooksForUser(String username) {
        return recipeBookRepository.findByRecipeBookOwner(username);
    }

    public Optional<String> getRecipeBookOwner(Integer recipeBookId) {
        return recipeBookRepository.findOwnerById(recipeBookId);
    }

    public List<RecipeBook> searchRecipeBookByNameRecipeBookOwner(String name, String recipeBookOwner) {
        return this.recipeBookRepository.findByNameContainingAndRecipeBookOwnerContainingAllIgnoreCase(name, recipeBookOwner);
    }

    public void addRecipeToRecipeBook(Integer recipeBookId, Integer recipeId) {
        if(!this.recipeBookRecipeRepository.existsByRecipeBookIdAndRecipeId(recipeBookId, recipeId)) {
            this.recipeBookRecipeRepository.save(new RecipeBookRecipe(recipeBookId, recipeId));
        } else {
            throw new IllegalArgumentException("The recipe is already in the recipe book.");
        }
    }

    public void addRecipeToDefaultRecipeBook(String recipeBookOwner, Integer recipeId) {
        Optional<Integer> recipeBookId = this.recipeBookRepository.findIdByRecipeBookOwnerAndName(recipeBookOwner, "My recipes");
        if (recipeBookId.isPresent()) {
            this.addRecipeToRecipeBook(recipeBookId.get(), recipeId);
        } else {
            throw new IllegalArgumentException("Recipe book 'My recipes' not found for user " + recipeBookOwner);
        }
    }

    public boolean isRecipeBookOwner(Integer recipeBookId, String username) {
        return this.recipeBookRepository.existsByIdAndRecipeBookOwner(recipeBookId, username);
    }

    public List<Recipe> getRecipesInRecipeBook(Integer recipeBookId) {
        return this.recipeBookRecipeRepository.findAllByRecipeBookId(recipeBookId)
                .stream()
                .map(rbr -> this.recipeRepository.findById(rbr.getRecipeId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }


    public boolean recipeExists(Integer id) {
        return this.recipeRepository.existsById(id);
    }

    public List<RecipeBook> getAllRecipeBooks() {
        return this.recipeBookRepository.findAll();
    }
}
