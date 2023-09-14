package thequeuers.seatsecure.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    protected String username;
    protected String password;
    protected String fullName;
    protected String email;
    protected String gender;
    protected Integer phoneNumber;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
}
