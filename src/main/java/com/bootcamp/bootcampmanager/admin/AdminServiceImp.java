package com.bootcamp.bootcampmanager.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImp implements AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImp(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    @Override
    public Admin getAdminById(long id) {
        Optional<Admin> optional = adminRepository.findById(id);
        Admin admin;
        if (optional.isPresent()) {
            admin = optional.get();
        } else {
            throw new RuntimeException("Not found admin: " + id);
        }
        return admin;
    }

    @Override
    public void deleteAdminById(long id) {
        adminRepository.deleteById(id);
    }
}
