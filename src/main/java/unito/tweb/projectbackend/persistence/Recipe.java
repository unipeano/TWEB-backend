package unito.tweb.projectbackend.persistence;


import jakarta.persistence.*;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "instructions", nullable = false)
    private String instructions;

    @Column(name = "preparation_time", nullable = false)
    private Integer preparationTime;

    @Column(name = "servings", nullable = false)
    private Integer servings;

    @Column(name = "author", nullable = false)
    private String author;

    public Recipe() {
    }

    public Recipe(String title, String description, String image, String instructions, Integer preparationTime, Integer servings, String author) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.instructions = instructions;
        this.author = author;
        this.preparationTime = preparationTime;
        this.servings = servings;
    }

    public Integer getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getImage() {
        return image;
    }
    public String getAuthor() {
        return author;
    }
    public String getInstructions() {
        return instructions;
    }
    public Integer getPreparationTime() {
        return preparationTime;
    }
    public Integer getServings() {
        return servings;
    }
}
