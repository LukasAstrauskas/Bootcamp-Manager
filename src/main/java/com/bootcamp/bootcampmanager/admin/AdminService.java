package com.bootcamp.bootcampmanager.admin;

import java.util.List;

public interface AdminService {

    List<Admin> getAllAdmins();

    void saveAdmin(Admin admin);

    Admin getAdminById(long id);

    void deleteAdminById(long id);
}
