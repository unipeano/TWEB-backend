package unito.tweb.projectbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeBookRecipeRepository extends JpaRepository<RecipeBookRecipe, Integer> {
    void deleteAllByRecipeId(Integer recipeId);
    boolean existsByRecipeBookIdAndRecipeId(Integer recipeBookId, Integer recipeId);
    List<RecipeBookRecipe> findAllByRecipeBookId(Integer recipeBookId);
}
