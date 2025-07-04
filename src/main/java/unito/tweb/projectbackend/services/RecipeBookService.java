package unito.tweb.projectbackend.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import unito.tweb.projectbackend.persistence.*;

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
        this.createRecipeBook("Favorites", "Mattia");
        this.recipeBookRecipeRepository.save(new RecipeBookRecipe(1, 1));
    }

    public RecipeBook createRecipeBook(String name, String recipeBookOwner) {
        RecipeBook recipeBook = new RecipeBook(name, recipeBookOwner);
        return recipeBookRepository.save(recipeBook);
    }


}
