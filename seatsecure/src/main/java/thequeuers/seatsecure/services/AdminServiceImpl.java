package thequeuers.seatsecure.services;

import java.util.List;
import org.springframework.stereotype.Service;

import thequeuers.seatsecure.entities.Admin;
import thequeuers.seatsecure.entities.Customer;
import thequeuers.seatsecure.repositories.AdminRepository;
import thequeuers.seatsecure.repositories.CustomerRepository;



@Service
public class AdminServiceImpl implements AdminService {
   
    private AdminRepository adminRepo;
    private CustomerRepository customerRepo;
    

    public AdminServiceImpl(AdminRepository adminRepo){
        this.adminRepo = adminRepo;
    }


    @Override
    public List<Customer> listCustomers() {
        return customerRepo.findAll();
    }

    
    @Override
    public Admin getAdminById(Long adminId){
        // Using Java Optional, as "findById" of Spring JPA returns an Optional object
        // Optional forces developers to explicitly handle the case of non-existent values
        // Here is an implementation using lambda expression to extract the value from Optional<Book>
        return adminRepo.findById(adminId).map(a -> {
            return a;
        }).orElse(null);
    }
    
    @Override
    public Admin addAdmin(Admin a) {
        return adminRepo.save(a);
    }
    
    @Override
    public Admin updateAdmin(Long adminId, Admin newAdminInfo){
        return adminRepo.findById(adminId).map(a -> {a.setPassword(newAdminInfo.getPassword());
            return adminRepo.save(a);
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
    public Admin deleteAdminById(Long adminId){
        Admin admin = getAdminById(adminId);
        adminRepo.deleteById(adminId);
        return admin;
    }
}