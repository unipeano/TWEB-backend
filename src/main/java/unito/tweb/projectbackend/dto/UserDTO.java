package unito.tweb.projectbackend.dto;

public class UserDTO {

    private String username;
    private String role;
    private String description;
    private String image;

    public UserDTO() {
    }

    public UserDTO(String username, String role, String description, String image) {
        this.username = username;
        this.role = role;
        this.description = description;
        this.image = image;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
