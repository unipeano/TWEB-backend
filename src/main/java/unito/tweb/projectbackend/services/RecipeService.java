package unito.tweb.projectbackend.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import unito.tweb.projectbackend.persistence.Recipe;
import unito.tweb.projectbackend.persistence.RecipeRepository;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @PostConstruct
    public void init() {
        recipeRepository.save(new Recipe("Pasta al Pomodoro", "A simple pasta dish with tomato sauce", "1.png", "Boil pasta, make sauce, combine.", 15, "Mattia"));
        recipeRepository.save(new Recipe("Pasta al Pesto", "A simple pasta dish with pesto sauce", "2.png", "Boil pasta, make sauce, combine.", 15, "Mattia"));
        recipeRepository.save(new Recipe("Pizza Margherita", "A classic pizza with tomato, mozzarella, and basil", "3.png", "Prepare dough, add toppings, bake.", 20, "Pietro"));
        recipeRepository.save(new Recipe("Risotto alla Milanese", "A creamy risotto with saffron", "4.png", "Cook rice, add broth, stir in saffron.", 30, "Pietro"));
    }

}
