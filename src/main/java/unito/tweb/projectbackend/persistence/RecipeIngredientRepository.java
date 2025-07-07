package unito.tweb.projectbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer> {
    void deleteAllByRecipeId(Integer recipeId);

    List<RecipeIngredient> findByRecipeId(Integer recipeId);
}
