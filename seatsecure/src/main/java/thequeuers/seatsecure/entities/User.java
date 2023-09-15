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
@Size(max = 30, message = "Fields should not be longer than 30 characters!")
public class User {

    @Pattern(regexp = "^[0-9A-Za-z_]+$", message = "Only alphabets, numbers and underscores are allowed!")
    private String username;
    
    private String password;

    @Pattern(regexp = "^[A-Za-z ]+$", message = "Only alphabets and spaces are allowed!")
    private String fullName;

    @Email(message = "Invalid email address!")
    private String email;

    @Pattern(regexp = "^(Male|Female)$", message = "An error has occurred. Please try again.") 
    private String gender; // Should be implemented with dropdown boxes (fixed options)

    @Pattern(regexp = "^[8-9][0-9]{7}$", message = "Invalid phone number! (please removing all spacings)")
    private Integer phoneNumber;
    
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
