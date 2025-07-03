package unito.tweb.projectbackend.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "recipe_categories")
public class RecipeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "recipe_id", nullable = false)
    private Integer recipeId;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    public RecipeCategory() {}

    public RecipeCategory(Integer recipeId, Integer categoryId) {
        this.recipeId = recipeId;
        this.categoryId = categoryId;
    }
    public Integer getId() {
        return id;
    }
    public Integer getRecipeId() {
        return recipeId;
    }
    public Integer getCategoryId() {
        return categoryId;
    }

}
