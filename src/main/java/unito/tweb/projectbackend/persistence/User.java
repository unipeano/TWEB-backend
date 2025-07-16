package unito.tweb.projectbackend.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;


    public User() {}

    // per chef e user
    public User(String username, String password, String role, String description, String image) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.description = description;
        this.image = image;
    }

    // per admin
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @JsonIgnore
    public boolean isAdmin() {
        return role.equals("ADMIN");
    }

    @JsonIgnore
    public boolean isUser() {
        return role.equals("USER");
    }

    @JsonIgnore
    public boolean isChef() {
        return role.equals("CHEF");
    }

    public void setChefRole(){
        this.role = "CHEF";
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

}

