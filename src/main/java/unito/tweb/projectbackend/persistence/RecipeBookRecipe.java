package unito.tweb.projectbackend.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "recipe_book_recipes")
public class RecipeBookRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "recipe_book_id", nullable = false)
    private Integer recipeBookId;

    @Column(name = "recipe_id", nullable = false)
    private Integer recipeId;

    public RecipeBookRecipe() {}

    public RecipeBookRecipe(Integer recipeBookId, Integer recipeId) {
        this.recipeBookId = recipeBookId;
        this.recipeId = recipeId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getRecipeBookId() {
        return recipeBookId;
    }

    public Integer getRecipeId() {
        return recipeId;
    }
}
