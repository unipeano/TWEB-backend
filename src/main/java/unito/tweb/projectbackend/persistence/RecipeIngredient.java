package unito.tweb.projectbackend.persistence;


import jakarta.persistence.*;

@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "recipe_id", nullable = false)
    private Integer recipeId;

    @Column(name = "ingredient_id", nullable = false)
    private Integer ingredientId;

    @Column(name = "quantity", nullable = false)
    private String quantity;

    public RecipeIngredient() {}

    public RecipeIngredient(Integer recipeId, Integer ingredientId, String quantity) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public String getQuantity() {
        return quantity;
    }


}

