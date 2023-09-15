package com.seatsecure.seatsecure.services;

import java.util.List;

import com.seatsecure.seatsecure.entities.Admin;
import com.seatsecure.seatsecure.entities.Customer;


public interface AdminService {

    List<Admin> listAdmins();

    Admin getAdminById(Long id);
    Admin addAdmin(Admin c);
    Admin updateAdmin(Long id, Admin newAdminInfo);
    Admin deleteAdminById(Long id);
}