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

    @Column(name = "author", nullable = false)
    private String author;

    public Recipe() {
    }

    public Recipe(String title, String description, String image, String author) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.author = author;
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

}
