package unito.tweb.projectbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Integer> {
    void deleteAllByRecipeId(Integer recipeId);

    Optional<RecipeCategory> findByCategoryId(Integer categoryId);
}
