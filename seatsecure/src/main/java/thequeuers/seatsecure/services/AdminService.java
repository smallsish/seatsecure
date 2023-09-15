package thequeuers.seatsecure.services;

import java.util.List;

import thequeuers.seatsecure.entities.Admin;
import thequeuers.seatsecure.entities.Customer;


public interface AdminService {

    List<Admin> listAdmins();

    Admin getAdminById(Long id);
    Admin addAdmin(Admin c);
    Admin updateAdmin(Long id, Admin newAdminInfo);
    Admin deleteAdminById(Long id);
}