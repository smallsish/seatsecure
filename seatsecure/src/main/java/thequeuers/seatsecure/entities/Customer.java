package thequeuers.seatsecure.entities;

import javax.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
// @AllArgsConstructor
// @NoArgsConstructor

@EqualsAndHashCode(callSuper = false)
public class Customer extends User {

    public Customer() {

    }

    public Customer(String username, String password, String email, String fullName, int phoneNumber, String gender) {
        super(username, password, email, fullName, phoneNumber, gender);

    }
}