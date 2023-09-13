package thequeuers.seatsecure.user;

import java.util.List;

public interface UserService {
    List<User> listUsers();
    User getUserById(Long id);
    User addUser(User user);
    User updateUser(Long id, User newUserInfo);
    User deleteUserById(Long id);
}