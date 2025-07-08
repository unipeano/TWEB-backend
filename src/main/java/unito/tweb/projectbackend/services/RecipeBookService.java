package unito.tweb.projectbackend.services;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import unito.tweb.projectbackend.persistence.*;

import java.util.Collections;
import java.util.List;

@Service
public class RecipeBookService {

    private final RecipeBookRepository recipeBookRepository;
    private final RecipeBookRecipeRepository recipeBookRecipeRepository;

    public RecipeBookService(RecipeBookRecipeRepository recipeBookRecipeRepository,
                         RecipeBookRepository recipeBookRepository) {
        this.recipeBookRecipeRepository = recipeBookRecipeRepository;
        this.recipeBookRepository = recipeBookRepository;
    }



    @PostConstruct
    public void init() {
        this.createRecipeBook("My recipes", "Mattia");
        this.createRecipeBook("Favorites", "Mattia");
        this.createRecipeBook("My recipes", "Pietro");
        this.recipeBookRecipeRepository.save(new RecipeBookRecipe(1, 1));
    }

    public RecipeBook createRecipeBook(String name, String recipeBookOwner) {
        if(!this.searchRecipeBookByNameRecipeBookOwner(name, recipeBookOwner).isEmpty()) {
            throw new IllegalArgumentException("A recipe book with the same name by the same owner already exists.");
            // consigliabile anche stampare a video la stack trace dell'eccezione, per consentirvi il debugging
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

    public List<RecipeBook> searchRecipeBookByNameRecipeBookOwner(String name, String recipeBookOwner) {
        return this.recipeBookRepository.findByNameContainingAndRecipeBookOwnerContainingAllIgnoreCase(name, recipeBookOwner);
    }
}
