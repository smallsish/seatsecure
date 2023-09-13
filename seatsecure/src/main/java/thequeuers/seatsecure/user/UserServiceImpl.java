package thequeuers.seatsecure.user;

import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
   
    private UserRepository users;
    

    public UserServiceImpl(UserRepository users){
        this.users = users;
    }

    @Override
    public List<User> listUsers() {
        return users.findAll();
    }

    
    @Override
    public User getUserById(Long userId){
        // Using Java Optional, as "findById" of Spring JPA returns an Optional object
        // Optional forces developers to explicitly handle the case of non-existent values
        // Here is an implementation using lambda expression to extract the value from Optional<Book>
        return users.findById(userId).map(user -> {
            return user;
        }).orElse(null);
    }
    
    @Override
    public User addUser(User user) {
        return users.save(user);
    }
    
    @Override
    public User updateUser(Long id, User newUserInfo){
        return users.findById(id).map(user -> {user.setPassword(newUserInfo.getPassword());
            return users.save(user);
        }).orElse(null);

        /*
        // You can also handle Optional objects in this way
        //
        Optional<Book> b = books.findById(id);
        if (b.isPresent()){
            Book book = b.get();
            book.setTitle(newBookInfo.getTitle());
            return books.save(book);
        }else
            return null;*/
    }

    /**
     * Remove a book with the given id
     * Spring Data JPA does not return a value for delete operation
     * Cascading: removing a book will also remove all its associated reviews
     */
    @Override
    public User deleteUserById(Long id){
        User user = getUserById(id);
        users.deleteById(id);
        return user;
    }
}