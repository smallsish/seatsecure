package thequeuers.seatsecure.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class User {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String gender;
    private Integer phoneNumber;




    public User(){

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


