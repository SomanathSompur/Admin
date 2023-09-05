package com.softwiz.admin.service;


import com.softwiz.admin.entity.AdminUser;
import com.softwiz.admin.entity.Book;
import com.softwiz.admin.entity.User;
import com.softwiz.admin.repository.AdminUserRepository;
import com.softwiz.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    @Autowired
    UserRepository userRepository;

    public AdminUserService(AdminUserRepository adminUserRepository){
        this.adminUserRepository = adminUserRepository;
    }

    public AdminUser registerMainAdmin(AdminUser adminRegistrationRequest) {

        // Check password length
        if (adminRegistrationRequest.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password Minimum 6 characters required");
        }

        // Add your custom password conditions here
        if (!meetsCustomPasswordConditions(adminRegistrationRequest.getPassword())) {
            throw new IllegalArgumentException("Custom password conditions not met : " +
                    "It must contains at least one uppercase letter, one lowercase letter and one digit");
        }

        // Check if the username is already in use
        if (adminUserRepository.findByUsername(adminRegistrationRequest.getUsername()) != null) {
            throw new IllegalArgumentException("Username already taken");
        }

        // Check if the email is already in use
        if (adminUserRepository.findByEmail(adminRegistrationRequest.getEmail()) != null) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Check custom email condition logic here
        if (!meetsCustomEmailConditions(adminRegistrationRequest.getEmail())) {
            throw new IllegalArgumentException("Custom email conditions not met: " +
                    "It should have one letter & exactly one \"@\" symbol and it must be ends with \"gmail.com\" ");
        }

        // Set the role for the main Admin
        // adminRegistrationRequest.setRole("ADMIN");


        /*
        //Create a new admin user entry
        AdminUser adminUser = new AdminUser();
        adminUser.setFirstname(adminRegistrationRequest.getFirstname());
        adminUser.setLastname(adminRegistrationRequest.getLastname());
        adminUser.setRole(adminRegistrationRequest.getRole());
        adminUser.setEmail(adminRegistrationRequest.getEmail());
        adminUser.setUsername(adminRegistrationRequest.getUsername());
        adminUser.setPassword(adminRegistrationRequest.getPassword());

        //Save the user to the database
        adminUserRepository.save(adminUser);

     */

        // Register the main Admin
        return adminUserRepository.save(adminRegistrationRequest);
    }


    // Define your custom password conditions for Admin Registration
    private boolean meetsCustomPasswordConditions(String password) {
        // Add your custom password condition logic here
        // For example, ensure it contains at least one uppercase letter, one lowercase letter, and one digit
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    }



    // Define your custom email conditions for Admin Registration
    private boolean meetsCustomEmailConditions(String email) {
        // Custom email condition logic
        // For example, ensure it contains a specific domain and format
        String[] parts = email.split("@");

        if (parts.length != 2) {
            return false; // Invalid format, should have exactly one "@" symbol
        }

        String usernamePart = parts[0];
        String domainPart = parts[1];

        if (!domainPart.equals("gmail.com")) {
            return false; // Custom condition: Email domain must be ends with "example.com"
        }

        // Custom condition: Username part must contain letters and numbers only
        if (!usernamePart.matches("^[a-zA-Z0-9]+$")) {
            return false;
        }

        return true; // Custom conditions met
    }


    // Admin user delete account himself
    public void deleteAdminUserById(Long adminUserId) {
        // Check if a user with the provided User ID exists
        Optional<AdminUser> adminUserOptional = adminUserRepository.findById(adminUserId);
        if (!adminUserOptional.isPresent()) {
            throw new IllegalArgumentException("User not found with ID: " + adminUserId);
        }

        // Delete the user by ID
        adminUserRepository.deleteById(adminUserId);
    }


    // Updating Admin details to the existing admin with ID that should not repeat Username & Email

    /*
    public AdminUser updateAdminUser(Long adminUserId, AdminUser updatedAdminUser) {
        // Check if the Admin with the given ID exists
        if (!adminUserRepository.findById(adminUserId).isPresent()) {
            throw new IllegalArgumentException("Admin with ID " + adminUserId + " does not exist");
        }

        // Get the existing book
        AdminUser existingAdminUser = adminUserRepository.findById(adminUserId).get();

        // Update the book fields
        existingAdminUser.setFirstname(updatedAdminUser.getFirstname());
        existingAdminUser.setLastname(updatedAdminUser.getLastname());
        existingAdminUser.setRole(updatedAdminUser.getRole());
        existingAdminUser.setEmail(updatedAdminUser.getEmail());
        existingAdminUser.setUsername(updatedAdminUser.getUsername());
        existingAdminUser.setPassword(updatedAdminUser.getPassword());

        // Save and return the updated book
        return adminUserRepository.save(existingAdminUser);
    }
    */

    public boolean updateAdmin(Long id, AdminUser updatedAdminUser) {
        AdminUser existingAdminUser = adminUserRepository.findById(id).orElse(null);
        if (existingAdminUser != null) {
            // Check if the updated email and username are unique
            if (isEmailAndUsernameUnique(updatedAdminUser.getEmail(), updatedAdminUser.getUsername(), id)) {
                // Update the fields you want to allow updating
                existingAdminUser.setFirstname(updatedAdminUser.getFirstname());
                existingAdminUser.setLastname(updatedAdminUser.getLastname());
                existingAdminUser.setRole(updatedAdminUser.getRole());
                existingAdminUser.setEmail(updatedAdminUser.getEmail());
                existingAdminUser.setUsername(updatedAdminUser.getUsername());
                existingAdminUser.setPassword(updatedAdminUser.getPassword());

                adminUserRepository.save(existingAdminUser);
                return true;
            }
        }
        return false;
    }

    private boolean isEmailAndUsernameUnique(String email, String username, Long id) {
        AdminUser existingAdminWithEmail = adminUserRepository.findByEmail(email);
        AdminUser existingAdminWithUsername = adminUserRepository.findByUsername(username);

        // Check uniqueness by comparing with existing users
        return (existingAdminWithEmail == null || existingAdminWithEmail.getId().equals(id)) &&
                (existingAdminWithUsername == null || existingAdminWithUsername.getId().equals(id));
    }



    // Admin creates new User with new username & email
    public User createUser(User userRegistrationRequest) {
        // Check if a user with the same username already exists
        if (userRepository.findByUsername(userRegistrationRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        // Check if a user with the same email already exists
        if (userRepository.findByEmail(userRegistrationRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Add your custom password conditions here
        if (!customPasswordConditions(userRegistrationRequest.getPassword())) {
            throw new IllegalArgumentException("Custom password conditions not met : " +
                    "It must contains at least one uppercase letter, one lowercase letter and one digit");
        }

        // Check custom email condition logic here
        if (!customEmailConditions(userRegistrationRequest.getEmail())) {
            throw new IllegalArgumentException("Custom email conditions not met: " +
                    "It should have exactly one \"@\" symbol amd it must be ends with \"gmail.com\" ");
        }

        /*
        // Admin creates new User
        User user = new User();
        user.setUsername(userRegistrationRequest.getUsername());
        user.setPassword(userRegistrationRequest.getPassword());
        user.setEmail(userRegistrationRequest.getEmail());
        user.setIsEnable(userRegistrationRequest.getIsEnable());

         */

        // Save the new user
        return userRepository.save(userRegistrationRequest);
    }


    // Define your custom password conditions for User Registration
    private boolean customPasswordConditions(String password) {
        // Add your custom password condition logic here
        // For example, ensure it contains at least one uppercase letter, one lowercase letter, and one digit
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    }


    // Define your custom email conditions for User Registration
    private boolean customEmailConditions(String email) {
        // Custom email condition logic
        // For example, ensure it contains a specific domain and format
        String[] parts = email.split("@");

        if (parts.length != 2) {
            return false; // Invalid format, should have exactly one "@" symbol
        }

        String usernamePart = parts[0];
        String domainPart = parts[1];

        if (!domainPart.equals("gmail.com")) {
            return false; // Custom condition: Email domain must be ends with "example.com"
        }

        // Custom condition: Username part must contain letters and numbers only
        if (!usernamePart.matches("^[a-zA-Z0-9]+$")) {
            return false;
        }

        return true; // Custom conditions met
    }


    // Admin Delete existing user account by using User ID
    public void deleteUserById(Long userId) {
        // Check if a user with the provided User ID exists
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        // Delete the user by ID
        userRepository.deleteById(userId);
    }


    // Admin Gets or Search User details by using user ID
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // Admin should be able to view a list of all user accounts.
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<AdminUser> getAllAdminUsers() {
        return adminUserRepository.findAll();
    }

    // Admin should be able to disable/enable user accounts
    public void enableOrDisableUserById(Long userId, boolean enable) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEnable(enable);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

}