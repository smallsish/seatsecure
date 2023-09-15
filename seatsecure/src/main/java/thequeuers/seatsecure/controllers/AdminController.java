package thequeuers.seatsecure.controllers;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import thequeuers.seatsecure.entities.Admin;
import thequeuers.seatsecure.entities.Customer;
import thequeuers.seatsecure.exceptions.AdminNotFoundException;
import thequeuers.seatsecure.services.AdminService;
import thequeuers.seatsecure.services.CustomerService;



@RestController
public class AdminController {
    private AdminService adminService;
    private CustomerService customerService;

    public AdminController(AdminService as){
        this.adminService = as;
    }

    /**
     * List all admins in the system
     * @return list of all admins
     */
    // @GetMapping("/admins")
    // public List<Admin> getAdmins(){
    //     return adminService.listAdmins();
    // }

        /**
     * List all customers in the system
     * @return list of all customers
     */
    @GetMapping("/admins")
    public List<Admin> getAdmin(){
        return adminService.listAdmins();
    }

    /**
     * Search for book with the given id
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     * @return book with the given id
     */
    @GetMapping("/admins/{id}")
    public Admin getAdmin(@PathVariable Long id){
        Admin admin = adminService.getAdminById(id);


        // Need to handle "book not found" error using proper HTTP status code
        // In this case it should be HTTP 404
        if(admin == null) throw new AdminNotFoundException(id);
        return adminService.getAdminById(id);

    }
    /**
     * Add a new book with POST request to "/books"
     * Note the use of @RequestBody
     * @param book
     * @return list of all books
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admins")
    public Admin addAdmin(@RequestBody Admin admin){
        return adminService.addAdmin(admin);
    }

    /**
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     * @param newBookInfo
     * @return the updated, or newly added book
     */
    @PutMapping("/admins/{id}")
    public Admin updateAdmin(@PathVariable Long id, @RequestBody Admin newAdminInfo){
        Admin admin = adminService.updateAdmin(id, newAdminInfo);
        if(admin == null) throw new AdminNotFoundException(id);
        
        return admin;
    }

    /**
     * Remove a book with the DELETE request to "/books/{id}"
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     */
    @DeleteMapping("/admins/{id}")
    public void deleteAdmin(@PathVariable Long id){
        try{
            adminService.deleteAdminById(id);
         }catch(EmptyResultDataAccessException e) {
            throw new AdminNotFoundException(id);
         }
    }
}