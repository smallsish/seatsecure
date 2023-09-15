package thequeuers.seatsecure.entities;

import javax.persistence.Entity;
import lombok.*;


@Entity
@ToString
// @AllArgsConstructor
// @NoArgsConstructor

@EqualsAndHashCode(callSuper = false)
public class Admin extends User {

    public Admin(){
    }
    public Admin(String username, String password, String email, String fullName, int phoneNumber, String gender) {
        super(username,password,email,fullName,phoneNumber,gender);
    }
}