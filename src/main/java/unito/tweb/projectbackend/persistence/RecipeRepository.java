package unito.tweb.projectbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    List<Recipe> findByTitleContainingIgnoreCase(String title);
    List<Recipe> findByAuthorContainingAndTitleContainingAllIgnoreCase(String author, String title);
    List<Recipe> findByAuthorContainingIgnoreCase(String author);
}
