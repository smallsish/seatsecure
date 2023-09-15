package thequeuers.seatsecure.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NotNull
public class User {

    @Size(max = 20, message = "Username should not be longer than 30 characters!")
    @Pattern(regexp = "^[0-9A-Za-z_]+$", message = "Only alphabets, numbers and underscores are allowed!")
    private String username;
    
    @Size(max = 20, message = "Password should not be longer than 30 characters!")
    private String password;

    @Size(max = 20, message = "Name should not be longer than 30 characters!")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Only alphabets and spaces are allowed!")
    private String fullName;

    @Email(message = "Invalid email address!")
    private String email;

    private String gender; // Should be implemented with dropdown boxes (fixed options)

    private int phoneNumber;
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    public User() {

    }

    public User(String username, String password, String email, String fullName, int phoneNumber, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }
}
