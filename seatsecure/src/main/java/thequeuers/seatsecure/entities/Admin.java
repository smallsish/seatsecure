package thequeuers.seatsecure.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.*;


@Entity
@ToString
// @AllArgsConstructor
// @NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Admin extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    public Admin(){
    }
    public Admin(String username, String password, String email, String fullName, int phoneNumber, String gender) {
        super(username,password,email,fullName,phoneNumber,gender);
    }
}