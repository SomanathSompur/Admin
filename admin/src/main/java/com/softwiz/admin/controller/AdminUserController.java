package com.softwiz.admin.controller;


import com.softwiz.admin.dto.AdminLoginRequest;
import com.softwiz.admin.entity.AdminUser;
import com.softwiz.admin.entity.Book;
import com.softwiz.admin.entity.User;
import com.softwiz.admin.service.AdminAuthService;
import com.softwiz.admin.service.AdminUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/admin")

public class AdminUserController {

    private final AdminUserService adminUserService;
    private final AdminAuthService adminAuthService;

    @Autowired
    public AdminUserController(AdminUserService adminUserService, AdminAuthService adminAuthService){
        this.adminUserService = adminUserService;
        this.adminAuthService = adminAuthService;
    }


    // Admin user registration himself
    @PostMapping("/admin_register")
    public ResponseEntity<String> registerMainAdmin(@RequestBody AdminUser adminRegistrationRequest) {
        try {
            AdminUser registeredAdmin = adminUserService.registerMainAdmin(adminRegistrationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Main Admin registered with ID: "
                    + registeredAdmin.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Admin user logins himself it displays Username
    @PostMapping("/admin_login")
    public ResponseEntity<?> admin_login(@RequestBody AdminLoginRequest adminLoginRequest){
        try {
            String username = adminAuthService.authenticate(adminLoginRequest);
            Optional<?> user_created =Optional.ofNullable(username);
            return ResponseEntity.of(user_created);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }


    // Admin user delete account himself
    @DeleteMapping("/admin_delete/{adminUserId}")
    public ResponseEntity<String> deleteAdminUserById(@PathVariable Long adminUserId) {
        try {
            adminUserService.deleteAdminUserById(adminUserId);
            return ResponseEntity.ok("Admin account deleted successfully with ID : " + adminUserId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /*
    @PutMapping("/admin_update/{adminUserId}")
    public ResponseEntity<?> updateAdminUser(@PathVariable Long adminUserId, @RequestBody AdminUser updatedAdminUser) {
        try {
            AdminUser updated = adminUserService.updateAdminUser(adminUserId, updatedAdminUser);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

     */

    @PutMapping("/admin_update/{id}")
    public ResponseEntity<String> updateAdminUser(@PathVariable Long id, @RequestBody AdminUser updatedAdminUser) {
        boolean isUpdated = adminUserService.updateAdmin(id, updatedAdminUser);
        if (isUpdated) {
            return ResponseEntity.ok("Admin user details updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin user not found. Admin user Id with" +
                    "Admin username & email should match");
        }
    }


    // Shows all the existing Admin Details
    @GetMapping("/admin_details")
    public List<AdminUser> getAllAdminUsers() {
        return adminUserService.getAllAdminUsers();
    }


    // Admin creates new User with new username & email
    @PostMapping("/create_user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            User createdUser = adminUserService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered with ID: " + createdUser.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Admin Delete existing user account by using User ID
    @DeleteMapping("/delete_user/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId) {
        try {
            adminUserService.deleteUserById(userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    // Admin Gets or Search User details by using user ID
    @GetMapping("/user_details/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Optional<User> userOptional = adminUserService.getUserById(userId);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        }
    }


    // Admin should be able to view a list of all user accounts.
    @GetMapping("/user_details")
    public List<User> getAllUsers() {
        return adminUserService.getAllUsers();
    }


    // Admin should be able to disable/enable user accounts
    @PutMapping("/enable/{userId}")
    public ResponseEntity<String> enableUserById(@PathVariable Long userId) {
        try {
            adminUserService.enableOrDisableUserById(userId, true);
            return ResponseEntity.ok("User enabled successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/disable/{userId}")
    public ResponseEntity<String> disableUserById(@PathVariable Long userId) {
        try {
            adminUserService.enableOrDisableUserById(userId, false);
            return ResponseEntity.ok("User disabled successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}