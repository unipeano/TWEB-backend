package unito.tweb.projectbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeBookRepository extends JpaRepository<RecipeBook, Integer> {
    List<RecipeBook> findByRecipeBookOwner(String recipeBookOwner);

    List<RecipeBook> findByNameContainingAndRecipeBookOwnerContainingAllIgnoreCase(String name, String recipeBookOwner);
}
