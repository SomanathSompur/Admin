package com.softwiz.admin.service;

import com.softwiz.admin.dto.AdminLoginRequest;
import com.softwiz.admin.entity.AdminUser;
import com.softwiz.admin.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService {

    @Autowired
    AdminUserRepository adminUserRepository;
    public String authenticate(AdminLoginRequest adminLoginRequest) {

        AdminUser adminUser = adminUserRepository.findByEmail(adminLoginRequest.getEmail());
        return adminUser.getUsername();

    }
}