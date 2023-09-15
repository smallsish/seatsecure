package thequeuers.seatsecure.entities;

import javax.persistence.Entity;

import lombok.*;


@Entity
@ToString
// @AllArgsConstructor
// @NoArgsConstructor
@EqualsAndHashCode
public class Admin extends User {
    
    public Admin(String username, String password, String email, String fullName, int phoneNumber, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }
}