package andrii.dto;

import andrii.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

public class UserDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonFormat(pattern = "d.M.yyyy, h:mm:ss a")
    private LocalDateTime lastVisit;
    private String photo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User convertToEntity() {
        return new ModelMapper().map(this, User.class);
    }

    public static UserDTO convertToDTO(User user) {
        return new ModelMapper().map(user, UserDTO.class);
    }
}
