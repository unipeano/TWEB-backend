package unito.tweb.projectbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeBookRepository extends JpaRepository<RecipeBook, Integer> {
}
