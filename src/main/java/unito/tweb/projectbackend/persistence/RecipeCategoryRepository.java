package unito.tweb.projectbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Integer> {
    void deleteAllByRecipeId(Integer recipeId);

    List<RecipeCategory> findByCategoryId(Integer categoryId);
    List<RecipeCategory> findByRecipeId(Integer recipeId);
}
