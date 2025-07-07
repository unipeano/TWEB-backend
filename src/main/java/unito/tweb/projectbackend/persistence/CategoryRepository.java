package unito.tweb.projectbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);

    @Query("SELECT c.id FROM Category c WHERE c.name ILIKE :name")
    Optional<Integer> findIdByName(@Param("name") String name);

    boolean existsByNameIgnoreCase(String name);
}
