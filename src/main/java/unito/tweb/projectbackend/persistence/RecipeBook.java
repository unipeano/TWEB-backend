package unito.tweb.projectbackend.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "recipe_books")
public class RecipeBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "recipe_book_owner", nullable = false)
    private String recipeBookOwner;

    public RecipeBook() {}

    public RecipeBook(String name, String recipeBookOwner) {
        this.name = name;
        this.recipeBookOwner = recipeBookOwner;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getRecipeBookOwner() {
        return recipeBookOwner;
    }
}
