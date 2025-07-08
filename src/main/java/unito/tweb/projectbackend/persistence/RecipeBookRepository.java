package unito.tweb.projectbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecipeBookRepository extends JpaRepository<RecipeBook, Integer> {
    List<RecipeBook> findByRecipeBookOwner(String recipeBookOwner);
    @Query("SELECT rb.recipeBookOwner FROM RecipeBook rb WHERE rb.id = :id")
    Optional<String> findOwnerById(@Param("id") Integer id);
    List<RecipeBook> findByNameContainingAndRecipeBookOwnerContainingAllIgnoreCase(String name, String recipeBookOwner);
    boolean existsByIdAndRecipeBookOwner(Integer id, String recipeBookOwner);

    @Query("SELECT rb.id FROM RecipeBook rb WHERE rb.recipeBookOwner ILIKE :recipeBookOwner AND rb.name ILIKE :name")
    Optional<Integer> findIdByRecipeBookOwnerAndName(String recipeBookOwner, String name);
}
