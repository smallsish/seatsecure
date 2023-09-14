package thequeuers.seatsecure.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;


@Entity
@Getter
@Setter
@ToString
// @AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "USER_TABLE")
public class User {
    //private static final AtomicLong counter = new AtomicLong();
    private String username;
    private String password;
    private String fullName;
    private String email;
    private int phoneNumber;
    private String gender;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    public User(String username, String password, String email, String fullName, int phoneNumber, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        //userId = counter.incrementAndGet();
    }
}