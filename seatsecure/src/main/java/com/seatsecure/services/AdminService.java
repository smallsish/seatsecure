package com.seatsecure.services;

import java.util.List;

import com.seatsecure.entities.Admin;
import com.seatsecure.entities.Customer;


public interface AdminService {

    List<Admin> listAdmins();

    Admin getAdminById(Long id);
    Admin addAdmin(Admin c);
    Admin updateAdmin(Long id, Admin newAdminInfo);
    Admin deleteAdminById(Long id);
}