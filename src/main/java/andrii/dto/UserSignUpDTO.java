package andrii.dto;

import andrii.entities.User;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

public class UserSignUpDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDateTime lastVisit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
    }

    public User convertToEntity() {
        return new ModelMapper().map(this, User.class);
    }
}
