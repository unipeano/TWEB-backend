package unito.tweb.projectbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeBookRecipeRepository extends JpaRepository<RecipeBookRecipe, Integer> {
    void deleteAllByRecipeId(Integer recipeId);
}
