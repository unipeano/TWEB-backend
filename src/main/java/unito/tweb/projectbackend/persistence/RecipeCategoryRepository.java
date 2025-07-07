package unito.tweb.projectbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Integer> {
    void deleteAllByRecipeId(Integer recipeId);
}
